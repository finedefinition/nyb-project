export const FeatureSection = ({ formData, setFormData, featuredVessel }) => {
    return (
        <div className="feature-section">
            <label>
                Featured Vessel
                <div className="custom-toggle">
                    <label className={`toggle-label ${formData.featuredVessel ? 'active' : ''}`}>
                    <input
                            type="checkbox"
                            name="featuredVessel"
                            checked={featuredVessel}
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
    );
}