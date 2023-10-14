import React, { useState, useEffect } from 'react';
import AWS from 'aws-sdk';
import './YachtPage.css';

export const YachtPage = () => {
    const [goods, setGoods] = useState([]);
    const [imageUrls, setImageUrls] = useState([]);

    useEffect(() => {
        // Fetch data from the database and set it in the state
        fetch('https://nyb-project-production.up.railway.app/vessels/cards')
            .then((response) => response.json())
            .then((data) => {
                setGoods(data);
                fetchImageUrls(data);
            })
            .catch((error) => console.error('Error fetching data:', error));
    }, []);

    const fetchImageFromS3 = async (good) => {
        try {
            const response = await fetch('/config.json');
            if (!response.ok) {
                throw new Error('Failed to fetch configuration');
            }
            const config = await response.json();

            // Configure AWS SDK with the credentials and region
            AWS.config.update({
                accessKeyId: config.S3_KEY_ID,
                secretAccessKey: config.S3_KEY_SECRET,
                region: config.S3_REGION_NAME,
            });

            const s3 = new AWS.S3();
            const bucketName = 'nyb-basket';

            // Fetch the image from Amazon S3 based on the imageUrl from the good
            const s3Object = await s3.getObject({ Bucket: bucketName, Key: good.imageUrl }).promise();
            return URL.createObjectURL(new Blob([s3Object.Body]));
        } catch (error) {
            console.error('Error:', error);
            return null;
        }
    };

    const fetchImageUrls = async (goods) => {
        const imageUrls = await Promise.all(goods.map((good) => fetchImageFromS3(good)));
        setImageUrls(imageUrls);
    };

    return (
        <div>
            {/* Display all cards from the database in rows of four */}
            <div className="card-container">
                {goods.map((good, index) => (
                    <div key={good.id} className="GoodCard">
                        {imageUrls[index] ? (
                            <img src={imageUrls[index]} alt="Boat" className="GoodCard__img" />
                        ) : (
                            <p>Loading image...</p>
                        )}
                        <h3 className="GoodCard__name">{good.make} {good.model}</h3>
                        <div className="GoodCard__place">
                            {good.country},{good.state} | {good.year}
                        </div>
                        <div className="GoodCard__price">
                            <b>€{good.price}</b>
                        </div>
                    </div>
                ))}
            </div>
        </div>
    );
};
