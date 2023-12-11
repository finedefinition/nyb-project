import React, {useState, useEffect} from 'react';
import {useParams} from 'react-router-dom';
import AWS from 'aws-sdk';
import './FullCard.css';


export const FullCard = () => {
    const [boatData, setBoatData] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [imageUrl, setImageUrl] = useState(null);
    const {vessel_id} = useParams();

    async function fetchData() {
        try {
            const response = await fetch(`https://nyb-project-production.up.railway.app/vessels/${vessel_id}`);
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
    }, [vessel_id]);

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
                    <p className="form-row">{boatData.vessel_description}</p>
                </div>
            </div>
            <div className="form-columns">
                <div className="form-column">
                    <div className="form-row">
                        <label>MAKE</label>
                        {boatData.vessel_make}
                    </div>
                    <div className="form-row">
                        <label>LENGTH OVERALL</label>
                        {boatData.vessel_loa}
                    </div>
                    <div className="form-row">
                        <label>CABIN</label>
                        {boatData.vessel_cabin}
                    </div>
                </div>
                <div className="form-column">
                    <div className="form-row">
                        <label>MODEL</label>
                        {boatData.vessel_model}
                    </div>
                    <div className="form-row">
                        <label>BEAM</label>
                        {boatData.vessel_beam}
                    </div>
                    <div className="form-row">
                        <label>BERTH</label>
                        {boatData.vessel_berth}
                    </div>
                </div>
                <div className="form-column">
                    <div className="form-row">
                        <label>PRICE</label>
                        {boatData.vessel_price}
                    </div>
                    <div className="form-row">
                        <label>DRAFT</label>
                        {boatData.vessel_draft}
                    </div>
                </div>
                <div className="form-column">
                    <div className="form-row">
                        <label>YEAR</label>
                        {boatData.vessel_year}
                    </div>
                    <div className="form-row">
                        <label>KEEL TYPE</label>
                        {boatData.vessel_keel_type}
                    </div>
                </div>
                <div className="form-column">
                    <div className="form-row">
                        <label>COUNTRY</label>
                        {boatData.vessel_country}
                    </div>
                    <div className="form-row">
                        <div className="form-row">
                            <label>FUEL TYPE</label>
                            {boatData.vessel_fuel_type}
                        </div>
                    </div>
                </div>
                <div className="form-column">
                    <div className="form-row">
                        <label>STATE</label>
                        {boatData.vessel_town}
                    </div>
                    <div className="form-row">
                        <label>ENGINE QTY</label>
                        {boatData.vessel_engine}
                    </div>
                </div>
            </div>
        </div>
    );
};
