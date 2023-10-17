import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom'; // Import useParams
import './VesselForm.css';

export const VesselForm = ({ initialData }) => {
    const { id } = useParams(); // Extract 'id' from URL params

    const [formData, setFormData] = useState(initialData || {
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
        imageFile: null,
    });

    const [submitStatus, setSubmitStatus] = useState({
        status: null,
        message: '',
    });

    useEffect(() => {
        if (initialData) {
            // Set the form data when initialData is provided (e.g., when editing an existing vessel)
            setFormData(initialData);
        } else {
            // If initialData is not provided, fetch data from the server
            async function fetchData() {
                try {
                    const response = await fetch(`https://nyb-project-production.up.railway.app/vessels/${id}`, {
                        method: 'GET',
                    });
                    if (!response.ok) {
                        throw new Error(`Failed to fetch data: ${response.status}`);
                    }
                    const data = await response.json();

                    // Update the formData state with the retrieved data
                    setFormData(data);
                    console.log('Data fetched successfully:', data);
                } catch (error) {
                    console.error('Error fetching data:', error);
                }
            }

            fetchData();
        }
    }, [initialData, id]);


    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData({
            ...formData,
            [name]: value,
        });
    };

    const handleFileChange = (e) => {
        const file = e.target.files[0];
        setFormData({
            ...formData,
            imageFile: file,
        });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        console.log(formData);

        const formDataToSend = new FormData();
        for (const key in formData) {
            formDataToSend.append(key, formData[key]);
        }

        try {
            let response;
            if (initialData) {
                // If initialData is provided, this is an update operation
                response = await fetch(`https://nyb-project-production.up.railway.app/vessels/${initialData.id}`, {
                    method: 'PUT',
                    body: formDataToSend,
                });
            } else {
                // Otherwise, this is a create operation
                response = await fetch('https://nyb-project-production.up.railway.app/vessels', {
                    method: 'POST',
                    body: formDataToSend,
                });
            }

            if (response.status === 201 || response.status === 204) {
                setSubmitStatus({
                    status: 'success',
                    message: 'Boat is saved successfully!',
                });
            } else {
                setSubmitStatus({
                    status: 'error',
                    message: 'There was an error saving the boat.',
                });
            }
        } catch (error) {
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
                            onChange={(e) => setFormData({...formData, featuredVessel: e.target.checked})}
                        />
                        <span className="slider"></span>
                    </label>
                </div>
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
                    <input
                        type="file"
                        name="imageFile"
                        onChange={handleFileChange}
                    />
                </label>
            </div>
            <div className="form-row">
                <button type="submit">{initialData ? 'Update Vessel' : 'Create Vessel'}</button>
            </div>

            {submitStatus.status && (
                <div className={`submit-message ${submitStatus.status}`}>
                    {submitStatus.message}
                </div>
            )}
        </form>
    );
};