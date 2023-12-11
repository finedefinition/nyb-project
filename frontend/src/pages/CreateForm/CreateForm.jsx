import axios from 'axios';
import React, {useEffect, useState} from 'react';
import {useNavigate} from 'react-router-dom';
import './CreateForm.css';
import {ImageSection} from "../../components/CreateForm/ImageSection";
import {FeatureSection} from "../../components/CreateForm/FeatureSection";


export const CreateForm = () => {
    const fileInputRef = React.useRef(null);
    const [formErrors, setFormErrors] = useState({});
    const [imageUrl, setImageUrl] = useState('');
    const [keelTypes, setKeelTypes] = useState([]);
    const [fuelTypes, setFuelTypes] = useState([]);
    const navigate = useNavigate();
    const [formData, setFormData] = useState({
        featured: false,
        vessel_make: 'Hanse',
        vessel_model: '60',
        vessel_price: 120000,
        vessel_year: 2016,
        vessel_country: 'Norway',
        vessel_town: 'Oslo',
        vessel_loa: 15,
        vessel_beam: 3,
        vessel_draft: 3,
        vessel_cabin: 2,
        vessel_berth: 4,
        vessel_keel_type: 'All keel types',
        vessel_fuel_type: 'All fuel types',
        vessel_engine: 1,
        vessel_description: 'The best yacht ever. Create on webpage',
        imageFile: null,
    });

    const [submitStatus, setSubmitStatus] = useState({
        status: null,
        message: '',
    });




    useEffect(() => {
        fetch('https://nyb-project-production.up.railway.app/enumKeelTypes')
            .then(response => response.json())
            .then(data => {
                setKeelTypes(data);
            });
    }, []);

    useEffect(() => {
        fetch('https://nyb-project-production.up.railway.app/enumFuelTypes')
            .then(response => response.json())
            .then(data => {
                setFuelTypes(data);
            });
    }, []);

    const handleChange = (e) => {
        // Handle changes in form inputs
        const {name, value} = e.target;
        setFormData({
            ...formData,
            [name]: value,
        });
        console.log("From handleChange");
        console.log(formData);
    };

    const handleFileChange = (e) => {
        // Handle file input changes
        const file = e.target.files[0];
        setFormData({
            ...formData,
            imageFile: file,
        });
        console.log("From handleFileChange");
        console.log(formData);

    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        if (!formData.imageFile) {
            setSubmitStatus({
                status: 'error',
                message: 'Please select an image file.',
            });
            return;
        }

        // Extract the imageFile from formData
        const imageFile = formData.imageFile;
        delete formData.imageFile;  // Remove imageFile from formData to avoid appending it twice

        // Prepare the form data to be sent to the server
        const formDataToSend = new FormData();

        // Append vesselData and imageFile separately
        formDataToSend.append('vesselData', new Blob([JSON.stringify(formData)], { type: "application/json" }));
        formDataToSend.append('imageFile', imageFile);

        axios.post('https://nyb-project-production.up.railway.app/vessels', formDataToSend)
            .then(response => {
                if (response.status === 201 || response.status === 204) {
                    // Handle success
                    setSubmitStatus({
                        status: 'success',
                        message: 'Boat is saved successfully!',
                    });
                    const vesselId = response.data.id; // Example: Extracting id from the response data.
                    navigate(`/full-card/${vesselId}`);
                } else {
                    // Handle unexpected status code
                    setSubmitStatus({
                        status: 'error',
                        message: 'There was an error saving the boat.',
                    });
                }
            })
            .catch(error => {
                // Handle error from server
                if (error.response && error.response.data) {
                    const fieldErrors = {}; // Object to store individual field errors

                    // Check if there's an array of validation errors from the server
                    if (Array.isArray(error.response.data)) {
                        error.response.data.forEach(err => {
                            // Using the 'property' as the key and 'interpolatedMessage' or 'messageTemplate' as the value
                            fieldErrors[err.property] = err.interpolatedMessage || err.messageTemplate;
                        });
                    }

                    setFormErrors(fieldErrors);

                    // Extracting only the desired message part from the error
                    const errorMessage = extractErrorMessage(error.response.data.message);
                    setSubmitStatus({
                        status: 'error',
                        message: errorMessage || 'There was an error saving the boat.',
                    });
                } else {
                    setSubmitStatus({
                        status: 'error',
                        message: 'There was an error saving the boat.',
                    });
                    console.error('Error:', error);
                }
            });

// Helper function to extract the desired message from the error
        function extractErrorMessage(fullMessage) {
            const regex = /messageTemplate='(.*?)'/;
            const match = regex.exec(fullMessage);
            if (match && match[1]) {
                return match[1];
            }
            return fullMessage;  // return the full message if extraction fails
        }

    };


    const handleFileClear = () => {
        fileInputRef.current.value = '';  // Clear the input field
        setFormData({
            ...formData,
            imageFile: null  // Reset the 'imageFile' property in the state
        });
        setImageUrl('');  // Clear the image preview
    };

    return (
        <div className="container">
            <form onSubmit={handleSubmit} className="vessel-form">
                <div className="top-section">
                    <ImageSection formData={formData}
                                  handleFileChange={handleFileChange}
                                  handleFileClear={handleFileClear}
                                  fileInputRef={fileInputRef}/>
                    {/*Image section block*/}
                    {/*<div className="left-side">*/}
                    {/*    {formData.imageFile && (*/}
                    {/*        <div className="image-container">*/}
                    {/*            <img*/}
                    {/*                src={URL.createObjectURL(formData.imageFile)}*/}
                    {/*                alt="Vessel"*/}
                    {/*                className="vessel-image"*/}
                    {/*            />*/}
                    {/*        </div>*/}
                    {/*    )}*/}
                    {/*    <div className="upload-section">*/}
                    {/*        <label>*/}
                    {/*            Upload Image:*/}
                    {/*            <input*/}
                    {/*                ref={fileInputRef}*/}
                    {/*                type="file"*/}
                    {/*                name="imageFile"*/}
                    {/*                onChange={handleFileChange}*/}
                    {/*            />*/}
                    {/*        </label>*/}
                    {/*        {formData.imageFile && (*/}
                    {/*            <button type="button" onClick={handleFileClear} className="clear-file-btn">×</button>*/}
                    {/*        )}*/}
                    {/*    </div>*/}
                    {/*</div>*/}
                    <div className="right-side">
                        <FeatureSection formData={formData}
                                        setFormData={setFormData}
                                        featuredVessel={formData.featured}/>
                        {/*<div className="feature-section">*/}
                        {/*    <label>*/}
                        {/*        Featured Vessel*/}
                        {/*        <div className="custom-toggle">*/}
                        {/*            <label className={`toggle-label ${formData.featuredVessel ? 'active' : ''}`}>*/}
                        {/*                <input*/}
                        {/*                    type="checkbox"*/}
                        {/*                    name="featuredVessel"*/}
                        {/*                    checked={formData.featuredVessel}*/}
                        {/*                    onChange={(e) => setFormData({*/}
                        {/*                        ...formData,*/}
                        {/*                        featuredVessel: e.target.checked*/}
                        {/*                    })}*/}
                        {/*                />*/}
                        {/*                <span className="slider"></span>*/}
                        {/*            </label>*/}
                        {/*        </div>*/}
                        {/*    </label>*/}
                        {/*</div>*/}
                        <div className="form-row description-row">
                            <label>
                                Description
                                <textarea
                                    name="vesselDescription"
                                    value={formData.vessel_description}
                                    onChange={handleChange}
                                />
                            </label>
                        </div>
                    </div>
                </div>
                <div className="form-columns">
                    {/*First column*/}
                    <div className="form-column">
                        <div className="form-row">
                            <label>Make</label>
                            <input
                                type="text"
                                name="vesselMake"
                                onChange={handleChange}
                                value={formData.vessel_make}
                            />
                            {formErrors.vessel_make && <span className="error">{formErrors.vessel_make}</span>}
                        </div>
                        <div className="form-row">
                            <label>
                                Length Overall
                                <input
                                    type="number"
                                    name="vesselLengthOverall"
                                    value={formData.vessel_loa}
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
                                    value={formData.vessel_cabin}
                                    onChange={handleChange}
                                />
                            </label>
                        </div>
                    </div>
                    <div className="form-column">
                        {/* Fields for second column */}
                        <div className="form-row">
                            <label>
                                Model
                                <input
                                    type="text"
                                    name="vesselModel"
                                    value={formData.vessel_model}
                                    onChange={handleChange}
                                />
                            </label>
                            {formErrors.vessel_model &&
                                <span className="error">{formErrors.vessel_model}</span>}
                        </div>
                        <div className="form-row">
                            <label>
                                Beam
                                <input
                                    type="number"
                                    name="vesselBeam"
                                    value={formData.vessel_beam}
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
                                    value={formData.vessel_berth}
                                    onChange={handleChange}
                                />
                            </label>
                        </div>
                    </div>
                    <div className="form-column">
                        {/* Fields for third column */}
                        <div className="form-row">
                            <label>
                                Price
                                <input
                                    type="number"
                                    name="vesselPrice"
                                    value={formData.vessel_price}
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
                                    value={formData.vessel_draft}
                                    onChange={handleChange}
                                />
                            </label>
                        </div>
                    </div>
                    <div className="form-column">
                        {/* Fields for fourth column */}
                        <div className="form-row">
                            <label>
                                Year
                                <input
                                    type="number"
                                    name="vesselYear"
                                    value={formData.vessel_year}
                                    onChange={handleChange}
                                />
                                {formErrors.vessel_year && <span className="error">{formErrors.vessel_year}</span>}
                            </label>
                        </div>
                        <div className="form-row">
                            <label>
                                Keel Type
                                <select name="keelType" value={formData.vessel_keel_type} onChange={handleChange}>
                                    {keelTypes.map((keelType, index) => (
                                        <option key={index} value={keelType.value}>
                                            {keelType.value}
                                        </option>
                                    ))}
                                </select>
                            </label>
                        </div>
                    </div>
                    <div className="form-column">
                        {/* Fields for fifth column */}
                        <div className="form-row">
                            <label>
                                Location country
                                <input
                                    type="text"
                                    name="vesselLocationCountry"
                                    value={formData.vessel_country}
                                    onChange={handleChange}
                                />
                            </label>
                        </div>
                        <div className="form-row">
                            <label>
                                Fuel Type
                                <select name="fuelType" value={formData.vessel_fuel_type} onChange={handleChange}>
                                    {fuelTypes.map((fuelType, index) => (
                                        <option key={index} value={fuelType.value}>
                                            {fuelType.value}
                                        </option>
                                    ))}
                                </select>
                            </label>
                        </div>
                    </div>
                    <div className="form-column">
                        {/* Fields for sixth column */}
                        <div className="form-row">
                            <label>
                                Location state
                                <input
                                    type="text"
                                    name="vesselLocationState"
                                    value={formData.vessel_town}
                                    onChange={handleChange}
                                />
                            </label>
                        </div>
                        <div className="form-row">
                            <label>
                                Engine quantity
                                <input
                                    type="number"
                                    name="engineQuantity"
                                    value={formData.vessel_engine}
                                    onChange={handleChange}
                                />
                            </label>
                        </div>
                    </div>
                </div>
                <div className="button-container">
                    <button type="submit" className="create-button">Create Vessel</button>
                </div>
                {submitStatus.status && (
                    <div className={`submit-message ${submitStatus.status}`}>
                        <h4>{submitStatus.message}</h4>
                    </div>
                )}
            </form>
        </div>
    );
};
