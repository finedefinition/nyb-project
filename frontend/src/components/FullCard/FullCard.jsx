import React, { useState, useEffect } from 'react';
import AWS from 'aws-sdk';
import './FullCard.css'; // Add your styling

export const FullCard = () => {
    const [boatData, setBoatData] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [imageUrl, setImageUrl] = useState(null);

    useEffect(() => {
        async function fetchData() {
            try {
                // Fetch boat data from the API endpoint
                const response = await fetch('https://nyb-project-production.up.railway.app/vessels/106'); // Replace with your API endpoint
                if (!response.ok) {
                    throw new Error('Failed to fetch boat data');
                }
                const data = await response.json();
                setBoatData(data);
                setLoading(false);
            } catch (error) {
                setError(error);
                setLoading(false);
            }
        }

        async function fetchImage() {
            try {
                if (boatData && boatData.imageKey) {
                    // Fetch configuration from a JSON file (replace with your setup)
                    const response = await fetch('/config.json');
                    if (!response.ok) {
                        throw new Error('Failed to fetch configuration');
                    }
                    const config = await response.json();

                    // Configure AWS SDK with credentials and region
                    AWS.config.update({
                        accessKeyId: config.S3_KEY_ID,
                        secretAccessKey: config.S3_KEY_SECRET,
                        region: config.S3_REGION_NAME,
                    });

                    const s3 = new AWS.S3();
                    const bucketName = 'nyb-basket';

                    // Fetch the image from Amazon S3 based on the imageKey in boatData
                    const s3Object = await s3.getObject({ Bucket: bucketName, Key: boatData.imageKey }).promise();
                    const imageUrl = URL.createObjectURL(new Blob([s3Object.Body]));
                    setImageUrl(imageUrl);
                }
            } catch (error) {
                console.error('Error:', error);
            }
        }

        fetchData();
        fetchImage();
    }, [boatData]);

    return (
        <div className="FullCard">
            {loading && <p className="loading-text">Loading boat data...</p>}
            {error && <p className="error-text">Error: {error.message}</p>}
            {!loading && !error && boatData && imageUrl && (
                <div className="card-content">
                    <img src={imageUrl} alt="Boat" className="card-image" />
                    <div className="card-details">
                        <h3 className="card-title">
                            {boatData.vesselMake} {boatData.vesselModel}
                        </h3>
                        <div className="card-info">
                            <p>Price: €{boatData.vesselPrice}</p>
                            <p>Year: {boatData.vesselYear}</p>
                            <p>Country: {boatData.vesselLocationCountry}</p>
                            <p>State: {boatData.vesselLocationState}</p>
                            <p>Length Overall: {boatData.vesselLengthOverall} feet</p>
                            <p>Beam: {boatData.vesselBeam} feet</p>
                            <p>Draft: {boatData.vesselDraft} feet</p>
                            <p>Cabin: {boatData.vesselCabin}</p>
                            <p>Berth: {boatData.vesselBerth}</p>
                            <p>Keel Type: {boatData.vesselKeelType}</p>
                            <p>Fuel Type: {boatData.vesselFuelType}</p>
                            <p>Engine Quantity: {boatData.engineQuantity}</p>
                            <p className="description">{boatData.vesselDescription}</p>
                            <p>Created At: {new Date(boatData.createdAt).toLocaleString()}</p>
                        </div>
                    </div>
                </div>
            )}
        </div>
    );
};