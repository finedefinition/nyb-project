import axios from 'axios';
import React, {useState, useEffect, useMemo} from 'react'; // Import useMemo
import {useParams, useNavigate} from 'react-router-dom';
import AWS from 'aws-sdk';
import './UpdateForm.css';

export const UpdateForm = () => {
    const {id} = useParams() || {id: 'defaultId'};
    console.log('Initial ID from useParams:', id);
    const fileInputRef = React.useRef(null);
    const [formErrors, setFormErrors] = useState({});
    const [imageUrl, setImageUrl] = useState('');
    const s3 = useMemo(() => new AWS.S3(), []);
    const bucketName = 'nyb-basket';
    const navigate = useNavigate();
    const [keelTypes, setKeelTypes] = useState([]);
    const [fuelTypes, setFuelTypes] = useState([]);
    const [formData, setFormData] = useState(
        {
        featuredVessel: false,
        vesselMake: '',
        vesselModel: '',
        vesselPrice: 0,
        vesselYear: 0,
        vesselLocationCountry: '',
        vesselLocationState: '',
        vesselLengthOverall: 0,
        vesselBeam: 0,
        vesselDraft: 0,
        vesselCabin: 0,
        vesselBerth: 0,
        keelType: 'ALL_KEEL_TYPES',
        fuelType: 'ALL_FUEL_TYPES',
        engineQuantity: 0,
        vesselDescription: '',
        imageKey: '',
    }
    );

    const [submitStatus, setSubmitStatus] = useState({
        status: '',
        message: '',
    });

    useEffect(() => {
        fetch('https://nyb-project-production.up.railway.app/keelTypes')
            .then(response => response.json())
            .then(data => {
                console.log('Fetched keelTypes:', data);
                setKeelTypes(data);
            });
    }, []);

    useEffect(() => {
        fetch('https://nyb-project-production.up.railway.app/fuelTypes')
            .then(response => response.json())
            .then(data => {
                setFuelTypes(data);
            });
    }, []);

    const handleChange = (e) => {
        const {name, value} = e.target;
        console.log(`Changed ${name} to ${value}`);
        setFormData({
            ...formData,
            [name]: value,
        });
    };

    const handleFileChange = (e) => {
        const file = e.target.files[0];
        console.log('Selected file:', file);

        if (file) {
            const reader = new FileReader();

            reader.onloadend = () => {
                setImageUrl(reader.result);  // set the preview image
            };

            reader.readAsDataURL(file);  // read the file to data URL format

            setFormData({
                ...formData,
                imageFile: file, // Update the 'imageFile' property in the state
            });
        }
        console.log(formData); // Log to check
    };

    // Initialize AWS configuration
    useEffect(() => {
        async function initAWSConfig() {
            try {
                const response = await fetch('/config.json');
                if (!response.ok) {
                    throw Error('Failed to fetch configuration');
                }
                const config = await response.json();

                AWS.config.update({
                    accessKeyId: config.S3_KEY_ID,
                    secretAccessKey: config.S3_KEY_SECRET,
                    region: config.S3_REGION_NAME,
                });
            } catch (error) {
                console.error('Error initializing AWS configuration:', error);
            }
        }

        initAWSConfig();
    }, []);



    // Fetch existing image data and set it in the state
    useEffect(() => {
        async function fetchData() {
            try {
                const response = await fetch(`https://nyb-project-production.up.railway.app/vessels/${id}`);
                if (!response.ok) {
                    throw Error(`Failed to fetch data: ${response.status}`);
                }
                const data = await response.json();
                setFormData(data);

                // If an imageKey exists, fetch and display the image
                if (data.imageKey) {
                    try {
                        // Fetch the image using the imageKey
                        const s3Object = await s3.getObject({Bucket: bucketName, Key: data.imageKey}).promise();
                        const imageUrl = URL.createObjectURL(new Blob([s3Object.Body]));
                        setImageUrl(imageUrl);
                    } catch (error) {
                        console.error('Error fetching image:', error);
                    }
                }
            } catch (error) {
                console.error('Error fetching data:', error);
            }
        }

        fetchData();
    }, [id, s3, bucketName]);


    const handleUpdate = async () => {
        const vesselId = id;
        const formDataToSend = new FormData();

        if (formData.imageFile) {
            formDataToSend.append("imageFile", formData.imageFile);
        }

        const vesselData = {...formData};
        delete vesselData.imageFile;
        formDataToSend.append("vesselData", new Blob([JSON.stringify(formData)], { type: "application/json" }));

        axios.put(`https://nyb-project-production.up.railway.app/vessels/${vesselId}`, formDataToSend)
            .then(response => {
                console.log('Server response:', response);
                if (response.status === 200 && response.data) {
                    setSubmitStatus({
                        status: 'success',
                        message: 'Vessel is updated successfully!',
                    });
                    console.log("Send data to server");
                    console.log(vesselData);
                    console.log('Attempting to update vessel with ID:', vesselId);
                    navigate(`/full-card/${vesselId}`);
                } else {
                    setSubmitStatus({
                        status: 'error',
                        message: response.data.message || 'Failed to update the vessel data.',
                    });
                    console.log("Error data");
                    console.log(vesselData);
                }
            })
            .catch(error => {
                // Handle error from server
                console.error('Error during axios PUT:', error);
                const fieldErrors = {}; // Object to store individual field errors
                if (error.response && error.response.data) {
                    // Check if there's an array of validation errors from the server
                    if (Array.isArray(error.response.data)) {
                        error.response.data.forEach(err => {
                            fieldErrors[err.property] = err.interpolatedMessage || err.messageTemplate;
                        });
                    }

                    setFormErrors(fieldErrors);

                    // Extracting only the desired message part from the error
                    const errorMessage = extractErrorMessage(error.response.data.message);
                    setSubmitStatus({
                        status: 'error',
                        message: errorMessage || 'Failed to update the vessel data.',
                    });
                } else {
                    setSubmitStatus({
                        status: 'error',
                        message: 'An error occurred while updating the vessel data.',
                    });
                    console.error('Error:', error);
                }
            });
    }

    // Helper function to extract the desired message from the error
    function extractErrorMessage(fullMessage) {
        const regex = /messageTemplate='(.*?)'/;
        const match = regex.exec(fullMessage);
        if (match && match[1]) {
            return match[1];
        }
        return fullMessage;  // return the full message if extraction fails
    }


    const handleFileClear = () => {
        fileInputRef.current.value = '';  // Clear the input field
        setFormData({
            ...formData,
            imageFile: null  // Reset the 'imageFile' property in the state
        });
        setImageUrl('');  // Clear the image preview
    };

    const handleDelete = async () => {
        try {
            const vesselId = id;
            const response = await fetch(`https://nyb-project-production.up.railway.app/vessels/${vesselId}`, {
                method: 'DELETE'
            });

            if (response.status === 204) { // Check for 204 status now
                setSubmitStatus({
                    status: 'success',
                    message: 'Vessel is deleted successfully!',
                });
                navigate(`/yachts`);
            } else {
                setSubmitStatus({
                    status: 'error',
                    message: 'Failed to delete the vessel.',
                });
            }
        } catch (error) {
            setSubmitStatus({
                status: 'error',
                message: 'An error occurred while deleting the vessel.',
            });
            console.error('Error:', error);
        }
    };


    return (
        <div className="container">
            <form onSubmit={handleUpdate} className="vessel-form">
                <div className="top-section"> {/* Top section containing image, upload, and description */}
                    <div className="left-side"> {/* Image and upload section */}
                        {imageUrl && (
                            <div className="image-container">
                                <img src={imageUrl} alt="Vessel" className="vessel-image"/>
                            </div>
                        )}
                        <div className="upload-section">
                            <label>
                                Upload Image:
                                <input type="file" name="imageFile" onChange={handleFileChange} ref={fileInputRef}/>
                            </label>
                            {formData.imageFile && (
                                <button type="button" onClick={handleFileClear} className="clear-file-btn">×</button>
                            )}
                        </div>
                    </div>

                    <div className="right-side"> {/* Featured and description section */}
                        <div className="feature-section">
                            <label>
                                Featured
                                <div className="custom-toggle">
                                    <label className={`toggle-label ${formData.featuredVessel ? 'active' : ''}`}>
                                        <input
                                            type="checkbox"
                                            name="featuredVessel"
                                            checked={formData.featuredVessel}
                                            onChange={(e) => setFormData({...formData, featuredVessel: e.target.checked})}
                                        />
                                        <span className="slider"></span>
                                    </label>
                                </div>
                            </label>
                        </div>

                        <div className="form-row description-row">
                            <label>
                                Description
                                <textarea
                                    name="vesselDescription"
                                    value={formData.vesselDescription}
                                    onChange={handleChange}
                                />
                            </label>
                        </div>
                    </div>
                </div>
                <div className="form-columns"> {/* Wrapper for the columns */}
                    <div className="form-column"> {/* First column */}

                        <div className="form-row">
                            <label>
                                Vessel Make
                                <input
                                    type="text"
                                    name="vesselMake"
                                    value={formData.vesselMake}
                                    onChange={handleChange}
                                />
                            </label>
                        </div>
                        <div className="form-row">
                            <label>
                                Length Overall
                                <input
                                    type="number"
                                    name="vesselLengthOverall"
                                    value={formData.vesselLengthOverall}
                                    onChange={handleChange}
                                />
                            </label>
                        </div>
                        <div className="form-row">
                            <label>
                                Cabin
                                <input
                                    type="number"
                                    name="vesselCabin"
                                    value={formData.vesselCabin}
                                    onChange={handleChange}
                                />
                            </label>
                        </div>
                    </div>
                    <div className="form-column"> {/* Second column */}
                        <div className="form-row">
                            <label>
                                Model
                                <input
                                    type="text"
                                    name="vesselModel"
                                    value={formData.vesselModel}
                                    onChange={handleChange}
                                />
                            </label>
                        </div>
                        <div className="form-row">
                            <label>
                                Beam
                                <input
                                    type="number"
                                    name="vesselBeam"
                                    value={formData.vesselBeam}
                                    onChange={handleChange}
                                />
                            </label>
                        </div>
                        <div className="form-row">
                            <label>
                                Berth
                                <input
                                    type="number"
                                    name="vesselBerth"
                                    value={formData.vesselBerth}
                                    onChange={handleChange}
                                />
                            </label>
                        </div>
                    </div>
                    {/* Third column */}
                    <div className="form-column">
                        <div className="form-row">
                            <label>
                                Price
                                <input
                                    type="number"
                                    name="vesselPrice"
                                    value={formData.vesselPrice}
                                    onChange={handleChange}
                                />
                            </label>
                        </div>
                        <div className="form-row">
                            <label>
                                Draft
                                <input
                                    type="number"
                                    name="vesselDraft"
                                    value={formData.vesselDraft}
                                    onChange={handleChange}
                                />
                            </label>
                        </div>
                    </div>
                    {/* Fourth column */}
                    <div className="form-column">
                        <div className="form-row">
                            <label>
                                Year
                                <input
                                    type="number"
                                    name="vesselYear"
                                    value={formData.vesselYear}
                                    onChange={handleChange}
                                />
                            </label>
                        </div>
                        <div className="form-row">
                            <label>
                                Keel Type
                                <select name="keelType" value={formData.keelType} onChange={handleChange}>
                                    {/* Default option displaying the current keelType from formData */}
                                    <option value={formData.keelType}>
                                        {formData.keelType}
                                    </option>

                                    {/* Populating the rest of the options from keelTypes array */}
                                    {keelTypes.map((keelType, index) => (
                                        <option key={index} value={keelType.name}>
                                            {keelType.value}
                                        </option>
                                    ))}
                                </select>
                            </label>
                        </div>
                    </div>
                    {/* Fifth column */}
                    <div className="form-column">
                        <div className="form-row">
                            <label>
                                Location Country
                                <input
                                    type="text"
                                    name="vesselLocationCountry"
                                    value={formData.vesselLocationCountry}
                                    onChange={handleChange}
                                />
                            </label>
                        </div>
                        <div className="form-row">
                            <label>
                                Fuel Type
                                <select name="fuelType" value={formData.fuelType} onChange={handleChange}>
                                    {/* Default option displaying the current keelType from formData */}
                                    <option value={formData.fuelType}>
                                        {formData.fuelType}
                                    </option>

                                    {/* Populating the rest of the options from keelTypes array */}
                                    {fuelTypes.map((fuelType, index) => (
                                        <option key={index} value={fuelType.name}>
                                            {fuelType.value}
                                        </option>
                                    ))}
                                </select>
                            </label>
                        </div>
                    </div>
                    {/* Sixth column */}
                    <div className="form-column">
                        <div className="form-row">
                            <label>
                                Location State
                                <input
                                    type="text"
                                    name="vesselLocationState"
                                    value={formData.vesselLocationState}
                                    onChange={handleChange}
                                />
                            </label>
                        </div>
                        <div className="form-row">
                            <label>
                                Engine Quantity
                                <input
                                    type="number"
                                    name="engineQuantity"
                                    value={formData.engineQuantity}
                                    onChange={handleChange}
                                />
                            </label>
                        </div>
                    </div>
                </div>
                <div className="button-container">
                    <button type="button" onClick={handleUpdate} className="update-button">
                        Update
                    </button>
                    <button type="button" onClick={handleDelete} className="delete-button">
                        Delete
                    </button>
                </div>

                {submitStatus.status && (
                    <div className={`submit-message ${submitStatus.status}`}>
                        {submitStatus.message}
                    </div>
                )}
            </form>
        </div>
    );
};