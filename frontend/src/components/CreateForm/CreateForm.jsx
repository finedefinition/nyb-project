import React, {useState} from 'react';
import './CreateForm.css';
import {FuelType} from "../../enums/FuelType";
import {KeelType} from "../../enums/KeelType";

export const CreateForm = () => {
    const fileInputRef = React.useRef(null);
    const [imageUrl, setImageUrl] = useState('');
    const [formData, setFormData] = useState({
        featuredVessel: false,
        vesselMake: 'Hanse',
        vesselModel: '60',
        vesselPrice: 120000,
        vesselYear: 2016,
        vesselLocationCountry: 'Norway',
        vesselLocationState: 'Oslo',
        vesselLengthOverall: 15,
        vesselBeam: 3,
        vesselDraft: 3,
        vesselCabin: 2,
        vesselBerth: 4,
        vesselKeelType: 'ALL_KEEL_TYPES',
        vesselFuelType: 'ALL_FUEL_TYPES',
        keelType: 'ALL_KEEL_TYPES',
        fuelType: 'ALL_FUEL_TYPES',
        engineQuantity: 1,
        vesselDescription: 'The best yacht ever',
        imageFile: null,
    });

    const [submitStatus, setSubmitStatus] = useState({
        status: null,
        message: '',
    });
    // Add select input for FuelType
    const fuelTypeOptions = Object.values(FuelType).map(fuelType => (
        <option key={fuelType} value={fuelType}>{fuelType}</option>
    ));

    // Add select input for KeelType
    const keelTypeOptions = Object.values(KeelType).map(keelType => (
        <option key={keelType} value={keelType}>{keelType}</option>
    ));

    const handleChange = (e) => {
        // Handle changes in form inputs
        const {name, value} = e.target;
        setFormData({
            ...formData,
            [name]: value,
        });
    };

    const handleFileChange = (e) => {
        // Handle file input changes
        const file = e.target.files[0];
        setFormData({
            ...formData,
            imageFile: file,
        });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        console.log(formData);

        if (!formData.imageFile) {
            // Handle the case where 'imageFile' is not present
            setSubmitStatus({
                status: 'error',
                message: 'Please select an image file.',
            });
            return;
        }

        // Prepare the form data to be sent to the server
        const formDataToSend = new FormData();
        for (const key in formData) {
            formDataToSend.append(key, formData[key]);
        }

        try {
            // This is a create operation
            const response = await fetch('https://nyb-project-production.up.railway.app/api/vessels', {
                method: 'POST',
                body: formDataToSend,
            });

            if (response.status === 201 || response.status === 204) {
                // Handle success
                setSubmitStatus({
                    status: 'success',
                    message: 'Boat is saved successfully!',
                });

            } else {
                // Handle error
                setSubmitStatus({
                    status: 'error',
                    message: 'There was an error saving the boat.',
                });
            }
        } catch (error) {
            // Handle error
            setSubmitStatus({
                status: 'error',
                message: 'There was an error saving the boat.',
            });
            console.error('Error:', error);
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
                    <div className="left-side">
                        {formData.imageFile && (
                            <div className="image-container">
                                <img
                                    src={URL.createObjectURL(formData.imageFile)}
                                    alt="Vessel"
                                    className="vessel-image"
                                />
                            </div>
                        )}
                        <div className="upload-section">
                            <label>
                                Upload Image:
                                <input
                                    ref={fileInputRef}
                                    type="file"
                                    name="imageFile"
                                    onChange={handleFileChange}
                                />
                            </label>
                            {formData.imageFile && (
                                <button type="button" onClick={handleFileClear} className="clear-file-btn">×</button>
                            )}
                        </div>
                    </div>
                    <div className="right-side">
                        <div className="feature-section">
                            <label>
                                Featured Vessel
                                <div className="custom-toggle">
                                    <label className={`toggle-label ${formData.featuredVessel ? 'active' : ''}`}>
                                        <input
                                            type="checkbox"
                                            name="featuredVessel"
                                            checked={formData.featuredVessel}
                                            onChange={(e) => setFormData({
                                                ...formData,
                                                featuredVessel: e.target.checked
                                            })}
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
                <div className="form-columns">
                    {/*First column*/}
                    <div className="form-column">
                        <div className="form-row">
                            <label>
                                Make
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
                    <div className="form-column">
                        {/* Fields for second column */}
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
                    <div className="form-column">
                        {/* Fields for third column */}
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
                    <div className="form-column">
                        {/* Fields for fourth column */}
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
                                <select
                                    name="keelType"
                                    value={formData.keelType}
                                    onChange={handleChange}
                                >
                                    {keelTypeOptions}
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
                                    value={formData.vesselLocationCountry}
                                    onChange={handleChange}
                                />
                            </label>
                        </div>
                        <div className="form-row">
                            <label>
                                Fuel Type
                                <select
                                    name="fuelType"
                                    value={formData.fuelType}
                                    onChange={handleChange}
                                >
                                    {fuelTypeOptions}
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
                                    value={formData.vesselLocationState}
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
                                    value={formData.engineQuantity}
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
