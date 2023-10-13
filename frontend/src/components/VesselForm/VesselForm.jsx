import React, {useState} from 'react';
import axios from 'axios';
import './VesselForm.css';

export const VesselForm = () => {
    const [formData, setFormData] = useState({
        featuredVessel: false, // Default to false or an appropriate default value
        vesselMake: '',
        vesselModel: '',
        vesselPrice: 0, // Default to 0 or an appropriate default value
        vesselYear: 0, // Default to 0 or an appropriate default value
        vesselLocationCountry: '',
        vesselLocationState: '',
        vesselLengthOverall: 0, // Default to 0 or an appropriate default value
        vesselBeam: 0, // Default to 0 or an appropriate default value
        vesselDraft: 0, // Default to 0 or an appropriate default value
        vesselCabin: 0, // Default to 0 or an appropriate default value
        vesselBerth: 0, // Default to 0 or an appropriate default value
        vesselKeelType: '',
        vesselFuelType: '',
        engineQuantity: 0, // Default to 0 or an appropriate default value
        vesselDescription: '',
        imageFile: null,
    });

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
            imageFile: file,
        });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        console.log(formData);

        try {
            const response = await axios.post('https://nyb-project-production.up.railway.app/vessels',
                {
                    featuredVessel: formData.featuredVessel,
                    vesselMake: formData.vesselMake,
                    vesselModel: formData.vesselModel,
                    vesselPrice: formData.vesselPrice,
                    vesselYear: formData.vesselYear,
                    vesselLocationCountry: formData.vesselLocationCountry,
                    vesselLocationState: formData.vesselLocationState,
                    vesselLengthOverall: formData.vesselLengthOverall,
                    vesselBeam: formData.vesselBeam,
                    vesselDraft: formData.vesselDraft,
                    vesselCabin: formData.vesselCabin,
                    vesselBerth: formData.vesselBerth,
                    vesselKeelType: formData.vesselKeelType,
                    vesselFuelType: formData.vesselFuelType,
                    engineQuantity: formData.engineQuantity,
                    vesselDescription: formData.vesselDescription,
                    imageFile: formData.imageFile,

                });
            if (response.status === 201) {
                // Handle success, e.g., show a success message or redirect to the newly created vessel page
            }
        } catch (error) {
            // Handle error, e.g., display an error message
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
                <button type="submit">Create Vessel</button>
            </div>
        </form>
    );
};

