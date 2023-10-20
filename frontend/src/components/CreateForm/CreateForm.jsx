import React, { useState } from 'react';
import './CreateForm.css';

export const CreateForm = () => {
    const [formData, setFormData] = useState({
        featuredVessel: false,
        vesselMake: 'Hanse',
        vesselModel: '60',
        vesselPrice: 12000,
        vesselYear: 2016,
        vesselLocationCountry: 'Ukraine',
        vesselLocationState: 'Odessa',
        vesselLengthOverall: 15,
        vesselBeam: 3,
        vesselDraft: 3,
        vesselCabin: 2,
        vesselBerth: 4,
        vesselKeelType: 'Fin',
        vesselFuelType: 'Diesel',
        engineQuantity: 1,
        vesselDescription: 'The best yacht ever',
        imageFile: null,
    });

    const [submitStatus, setSubmitStatus] = useState({
        status: null,
        message: '',
    });

    const handleChange = (e) => {
        // Handle changes in form inputs
        const { name, value } = e.target;
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
            const response = await fetch('https://nyb-project-production.up.railway.app/vessels', {
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

    return (
        <form onSubmit={handleSubmit} className="vessel-form">
            <div className="form-row">
                <label>
                    Featured Vessel:
                </label>
                <div className="custom-toggle">
                    <label className={`toggle-label ${formData.featuredVessel ? 'active' : ''}`}>
                        <input
                            type="checkbox"
                            name="featuredVessel"
                            checked={formData.featuredVessel}
                            onChange={(e) => setFormData({ ...formData, featuredVessel: e.target.checked })}
                        />
                        <span className="slider"></span>
                    </label>
                </div>
            </div>
            <div className="form-row">
                <label>
                    Upload Image:
                    <input
                        type="file"
                        name="imageFile"
                        onChange={handleFileChange}
                    />
                </label>
                {formData.imageFile && (
                    <img
                        src={URL.createObjectURL(formData.imageFile)}
                        alt="Image Preview"
                        className="image-preview"
                    />
                )}
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
                <button type="submit" className="create-button">Create Vessel</button>
            </div>

            {submitStatus.status && (
                <div className={`submit-message ${submitStatus.status}`}>
                    {submitStatus.message}
                </div>
            )}
        </form>
    );
};