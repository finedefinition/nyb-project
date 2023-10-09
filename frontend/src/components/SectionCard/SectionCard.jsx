import React, { useState, useEffect } from 'react';
import { Storage, Amplify } from 'aws-amplify'; // Import Amplify along with Storage
import awsconfig from './aws-exports'; // Import your Amplify configuration

import { GoodList } from '../GoodList';
import './SectionCard.css';

Amplify.configure(awsconfig); // Configure Amplify with your AWS configuration

export const SectionCard = () => {
  const [featuredBoats, setFeaturedBoats] = useState([]);

  useEffect(() => {
    // Log AWS credentials here
    console.log('AWS Credentials:', Amplify.config.credentials);

    fetch('https://nyb-project-production.up.railway.app/boats/featured')
        .then((response) => {
          if (!response.ok) {
            throw new Error('Network response was not ok');
          }
          return response.json();
        })
        .then(async (data) => {
          // Fetch the images for each boat using Amplify Storage
          const boatsWithImages = await Promise.all(
              data.map(async (boat) => {
                try {
                  const imageUrl = await Storage.get(boat.imageUrl);
                  return { ...boat, imageUrl };
                } catch (error) {
                  console.error('Error fetching image from S3:', error);
                  return boat; // Keep the boat data even if image retrieval fails
                }
              })
          );

          // Set the updated data in the state
          setFeaturedBoats(boatsWithImages);
        })
        .catch((error) => {
          console.error('Error fetching featured boats:', error);
        });
  }, []);

  return (
      <section className="section">
        <div className="section__up">
          <h2 className="section__title">Featured yachts</h2>
          <button className="section__btn">See all</button>
        </div>
        <GoodList goods={featuredBoats} />
      </section>
  );
};
