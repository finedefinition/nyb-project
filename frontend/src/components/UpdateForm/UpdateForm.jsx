import React, { useState, useEffect, useMemo } from 'react'; // Import useMemo
import {useParams} from 'react-router-dom';
import AWS from 'aws-sdk';
import './UpdateForm.css';

export const UpdateForm = () => {
    const {id} = useParams() || {id: 'defaultId'};

    const [formData, setFormData] = useState({
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
        vesselKeelType: '',
        vesselFuelType: '',
        engineQuantity: 0,
        vesselDescription: '',
        imageKey: '', // Add a field to store the image key
    });
// Define 's3' and 'bucketName' variables
    const s3 = useMemo(() => new AWS.S3(), []);
    const bucketName = 'nyb-basket';

    const [submitStatus, setSubmitStatus] = useState({
        status: '',
        message: '',
    });

    const [imageUrl, setImageUrl] = useState('');

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
                        const s3Object = await s3.getObject({ Bucket: bucketName, Key: data.imageKey }).promise();
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
        try {
            const vesselId = id;
            const formDataToSend = new FormData();

            // Check if a new image file is selected
            if (formData.imageFile) {
                formDataToSend.append("imageFile", formData.imageFile);
            }

            // Append other form fields
            for (const key in formData) {
                // Skip the 'imageFile' property if it's already included
                if (key !== "imageFile" && formData[key] !== null) {
                    formDataToSend.append(key, formData[key]);
                }
            }

            const response = await fetch(`https://nyb-project-production.up.railway.app/vessels/${vesselId}`, {
                method: 'PUT',
                body: formDataToSend,
            });

            if (response.status === 200) {
                setSubmitStatus({
                    status: 'success',
                    message: 'Vessel is updated successfully!',
                });
            } else {
                setSubmitStatus({
                    status: 'error',
                    message: 'Failed to update the vessel data.',
                });
            }
        } catch (error) {
            setSubmitStatus({
                status: 'error',
                message: 'An error occurred while updating the vessel data.',
            });
            console.error('Error:', error);
        }
    };

    const handleChange = (e) => {
        const {name, value} = e.target;
        setFormData({
            ...formData,
            [name]: value,
        });
    };

    const handleFileChange = (e) => {
        const file = e.target.files[0];
        setFormData({
            ...formData,
            imageFile: file, // Update the 'imageFile' property in the state
        });
    console.log(formData); // Log to check
};


    return (
        <div>
            <form onSubmit={handleUpdate} className="vessel-form">
                {imageUrl && (
                    <div className="form-row">
                        <label>Image:</label>
                        <img src={imageUrl} alt="Vessel" className="vessel-image"/>
                    </div>
                )}
                <div className="form-row">
                    <label>
                        Featured Vessel:
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
                <div className="form-row">
                    <label>
                        Vessel Make:
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
                        Vessel Model:
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
                        Vessel Price:
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
                        Vessel Year:
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
                        Vessel Location Country:
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
                        Vessel Location State:
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
                        Vessel Length Overall:
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
                        Vessel Beam:
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
                        Vessel Draft:
                        <input
                            type="number"
                            name="vesselDraft"
                            value={formData.vesselDraft}
                            onChange={handleChange}
                        />
                    </label>
                </div>
                <div className="form-row">
                    <label>
                        Vessel Cabin:
                        <input
                            type="number"
                            name="vesselCabin"
                            value={formData.vesselCabin}
                            onChange={handleChange}
                        />
                    </label>
                </div>
                <div className="form-row">
                    <label>
                        Vessel Berth:
                        <input
                            type="number"
                            name="vesselBerth"
                            value={formData.vesselBerth}
                            onChange={handleChange}
                        />
                    </label>
                </div>
                <div className="form-row">
                    <label>
                        Vessel Keel Type:
                        <input
                            type="text"
                            name="vesselKeelType"
                            value={formData.vesselKeelType}
                            onChange={handleChange}
                        />
                    </label>
                </div>
                <div className="form-row">
                    <label>
                        Vessel Fuel Type:
                        <input
                            type="text"
                            name="vesselFuelType"
                            value={formData.vesselFuelType}
                            onChange={handleChange}
                        />
                    </label>
                </div>
                <div className="form-row">
                    <label>
                        Engine Quantity:
                        <input
                            type="number"
                            name="engineQuantity"
                            value={formData.engineQuantity}
                            onChange={handleChange}
                        />
                    </label>
                </div>
                <div className="form-row">
                    <label>
                        Vessel Description:
                        <textarea
                            name="vesselDescription"
                            value={formData.vesselDescription}
                            onChange={handleChange}
                        />
                    </label>
                </div>
                <div className="form-row">
                    <label>
                        Upload Image:
                        <input type="file" name="imageFile" onChange={handleFileChange}/>
                    </label>
                </div>

                <button type="button" onClick={handleUpdate}>
                    Update
                </button>

                {submitStatus.status && (
                    <div className={`submit-message ${submitStatus.status}`}>
                        {submitStatus.message}
                    </div>
                )}
            </form>
        </div>
    );
};