import React, { useState, useEffect } from 'react';
import { GoodCard } from '../GoodCard';
import './GoodList.css';

export const GoodList = () => {
    const [, setGoods] = useState([]); // Removed 'goods' variable
    const [displayedGoods, setDisplayedGoods] = useState([]);

    useEffect(() => {
        // Function to retrieve and set featured cards from the database
        const fetchAndSetGoods = () => {
            fetch('https://nyb-project-production.up.railway.app/vessels/cards')
                .then((response) => response.json())
                .then((data) => {
                    setGoods(data);

                    // Filter and set only featured cards
                    const featuredData = data.filter((good) => good.featured === true);

                    // Randomly shuffle the filtered data and slice the first 4 cards
                    const shuffledData = shuffleArray(featuredData);
                    setDisplayedGoods(shuffledData.slice(0, 4));
                })
                .catch((error) => console.error('Error fetching data:', error));
        };

        // Function to shuffle an array
        const shuffleArray = (array) => {
            const shuffledArray = [...array];
            for (let i = shuffledArray.length - 1; i > 0; i--) {
                const j = Math.floor(Math.random() * (i + 1));
                [shuffledArray[i], shuffledArray[j]] = [shuffledArray[j], shuffledArray[i]];
            }
            return shuffledArray;
        };

        // Call the function to fetch and set the initial 4 featured cards
        fetchAndSetGoods();
    }, []);

    return (
        <div className="GoodList">
            <div className="card-container">
                {displayedGoods.map((good) => (
                    <GoodCard key={good.id} good={good} />
                ))}
            </div>
        </div>
    );
};
