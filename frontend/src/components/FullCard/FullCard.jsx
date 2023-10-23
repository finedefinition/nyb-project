import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import AWS from 'aws-sdk';
import './FullCard.css';

export const FullCard = () => {
    const [boatData, setBoatData] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [imageUrl, setImageUrl] = useState(null);
    const { id } = useParams();

    useEffect(() => {
        async function fetchData() {
            try {
                const response = await fetch(`https://nyb-project-production.up.railway.app/api/vessels/${id}`);
                if (!response.ok) {
                    throw new Error(`Failed to fetch boat data: ${response.status}`);
                }
                const data = await response.json();
                setBoatData(data);
            } catch (error) {
                setError(error);
            } finally {
                setLoading(false);
            }
        }

        async function fetchImage() {
            try {
                if (boatData && boatData.imageKey) {
                    const response = await fetch('/config.json');
                    if (!response.ok) {
                        throw  Error('Failed to fetch configuration');
                    }
                    const config = await response.json();

                    AWS.config.update({
                        accessKeyId: config.S3_KEY_ID,
                        secretAccessKey: config.S3_KEY_SECRET,
                        region: config.S3_REGION_NAME,
                    });

                    const s3 = new AWS.S3();
                    const bucketName = 'nyb-basket';

                    const s3Object = await s3.getObject({ Bucket: bucketName, Key: boatData.imageKey }).promise();
                    const imageUrl = URL.createObjectURL(new Blob([s3Object.Body]));
                    setImageUrl(imageUrl);
                }
            } catch (error) {
                setError(error);
            }
        }

        fetchData()
            .then(() => fetchImage())
            .catch((error) => setError(error))
            .finally(() => setLoading(false));
    }, [id, boatData]);

    if (loading) {
        return <p className="loading-text">Loading boat data...</p>;
    }

    if (error) {
        return <p className="error-text">Error: {error.message}</p>;
    }

    if (!boatData || !imageUrl) {
        return <p className="error-text">No data available.</p>;
    }

    return (
        <div className="FullCard">
            <div className="card-title">
               <h2>{boatData.vesselMake} {boatData.vesselModel}</h2><h3><i>{boatData.vesselLocationCountry},{boatData.vesselLocationState} €{boatData.vesselPrice}</i></h3>
            </div>
            <img src={imageUrl} alt="Boat" className="card-image" />
            <div className="card-details">

                <div className="card-info">
                    <div className="form-columns">
                        <div className="form-column">

                        </div>
                    <p className="card-info-item">Price: €{boatData.vesselPrice}</p>
                    <p className="card-info-item">Year: {boatData.vesselYear}</p>
                    <p className="card-info-item">Country: {boatData.vesselLocationCountry}</p>
                    <p className="card-info-item">State: {boatData.vesselLocationState}</p>
                    <p className="card-info-item">Length Overall: {boatData.vesselLengthOverall} feet</p>
                    <p className="card-info-item">Beam: {boatData.vesselBeam} feet</p>
                    <p className="card-info-item">Draft: {boatData.vesselDraft} feet</p>
                    <p className="card-info-item">Cabin: {boatData.vesselCabin}</p>
                    <p className="card-info-item">Berth: {boatData.vesselBerth}</p>
                    <p className="card-info-item">Keel Type: {boatData.vesselKeelType}</p>
                    <p className="card-info-item">Fuel Type: {boatData.vesselFuelType}</p>
                    <p className="card-info-item">Engine Quantity: {boatData.engineQuantity}</p>
                    <p className="description">{boatData.vesselDescription}</p>
                    <p className="card-info-item">Created At: {new Date(boatData.createdAt).toLocaleString()}</p>
                    </div>
                </div>
            </div>
        </div>
    );
};
