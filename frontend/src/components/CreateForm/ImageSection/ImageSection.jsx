export const ImageSection = ({ formData, handleFileChange, handleFileClear, fileInputRef }) => {
    return (
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
    );
}
