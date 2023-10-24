import React, {useState, useEffect} from 'react';
import {useParams} from 'react-router-dom';
import AWS from 'aws-sdk';
import './FullCard.css';

export const FullCard = () => {
    const [boatData, setBoatData] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [imageUrl, setImageUrl] = useState(null);
    const {id} = useParams();

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
        if (!boatData || !boatData.imageKey) return;
        try {
            const response = await fetch('/config.json');
            if (!response.ok) {
                throw new Error('Failed to fetch configuration');
            }
            const config = await response.json();

            AWS.config.update({
                accessKeyId: config.S3_KEY_ID,
                secretAccessKey: config.S3_KEY_SECRET,
                region: config.S3_REGION_NAME,
            });

            const s3 = new AWS.S3();
            const bucketName = 'nyb-basket';

            const s3Object = await s3.getObject({Bucket: bucketName, Key: boatData.imageKey}).promise();
            const imageUrl = URL.createObjectURL(new Blob([s3Object.Body]));
            setImageUrl(imageUrl);
        } catch (error) {
            setError(error);
        }
    }

    useEffect(() => {
        fetchData();
    }, [id]);

    useEffect(() => {
        if (boatData) {
            fetchImage();
        }
    }, [boatData]);

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
        <div className="container">
            <div className="top-section">
                <div className="left-side"> {/* Image*/}
                    <div className="image-container">
                        <img src={imageUrl} alt="Vessel" className="vessel-image"/>
                    </div>
                </div>
                <div className="right-side">
                    <label>ABOUT</label>
                    <p className="form-row">{boatData.vesselDescription}</p>
                </div>
            </div>
            <div className="form-columns">
                <div className="form-column">
                    <div className="form-row">
                        <label>MAKE</label>
                        {boatData.vesselMake}
                    </div>
                    <div className="form-row">
                        <label>LENGTH OVERALL</label>
                        {boatData.vesselLengthOverall}
                    </div>
                    <div className="form-row">
                        <label>CABIN</label>
                        {boatData.vesselCabin}
                    </div>
                </div>
                <div className="form-column">
                    <div className="form-row">
                        <label>MODEL</label>
                        {boatData.vesselModel}
                    </div>
                    <div className="form-row">
                        <label>BEAM</label>
                        {boatData.vesselBeam}
                    </div>
                    <div className="form-row">
                        <label>BERTH</label>
                        {boatData.vesselBerth}
                    </div>
                </div>
                <div className="form-column">
                    <div className="form-row">
                        <label>PRICE</label>
                        {boatData.vesselPrice}
                    </div>
                    <div className="form-row">
                        <label>DRAFT</label>
                        {boatData.vesselDraft}
                    </div>
                </div>
                <div className="form-column">
                    <div className="form-row">
                        <label>YEAR</label>
                        {boatData.vesselYear}
                    </div>
                    <div className="form-row">
                        <label>KEEL TYPE</label>
                        {boatData.vesselKeelType}
                    </div>
                </div>
                <div className="form-column">
                    <div className="form-row">
                        <label>COUNTRY</label>
                        {boatData.vesselLocationCountry}
                    </div>
                    <div className="form-row">
                        <label>FUEL TYPE</label>
                        {boatData.vesselFuelType}
                    </div>
                </div>
                <div className="form-column">
                    <div className="form-row">
                        <label>STATE</label>
                        {boatData.vesselLocationState}
                    </div>
                    <div className="form-row">
                        <label>ENGINE QTY</label>
                        {boatData.engineQuantity}
                    </div>
                </div>
            </div>
        </div>
    );
};
