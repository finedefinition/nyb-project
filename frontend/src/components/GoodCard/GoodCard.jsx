import React, { useState, useEffect } from 'react';
import AWS from 'aws-sdk';
import './GoodCard.css';

export const GoodCard = ({ good }) => {
    const [imageUrl, setImageUrl] = useState(null);

    useEffect(() => {
        // Configure AWS SDK with your credentials and S3 bucket information
        AWS.config.update({
            accessKeyId: 'AKIAUXLH7DAVIEHB5FF2',
            secretAccessKey: 'N7ARg0AZ02niNCLZznIUA3VUs0fC2we761Mz4Cwn',
            region: 'eu-north-1', // Change to your desired AWS region
        });

        const s3 = new AWS.S3();
        const bucketName = 'nyb-basket';

        // Fetch the image from Amazon S3 based on the imageUrl from the good prop
        async function fetchImageFromS3() {
            try {
                const s3Object = await s3
                    .getObject({ Bucket: bucketName, Key: good.imageUrl })
                    .promise();
                const imageUrl = URL.createObjectURL(new Blob([s3Object.Body]));
                setImageUrl(imageUrl);
            } catch (error) {
                console.error(
                    `Error fetching image ${good.imageUrl} from S3:`,
                    error
                );
            }
        }

        fetchImageFromS3();

    }, [good]);

    return (
        <div className="GoodCard">
            {imageUrl ? (
                <img src={imageUrl} alt="Boat" className="GoodCard__img" />
            ) : (
                <p>Loading image...</p>
            )}
            <h3 className="GoodCard__name">{good.name}</h3>
            <div className="GoodCard__place">
                {good.place} | {good.year}
            </div>
            <div className="GoodCard__price">
                <b>€{good.price}</b>
            </div>
        </div>
    );
};
