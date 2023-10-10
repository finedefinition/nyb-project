import React, { useState, useEffect } from 'react';
import { GoodCard } from '../GoodCard';
import './GoodList.css';

export const GoodList = () => {
    const [goods, setGoods] = useState([]);
    const [displayedGoods, setDisplayedGoods] = useState([]);

    // Function to retrieve and set 4 cards from the database
    const fetchAndSetGoods = () => {
        fetch('https://nyb-project-production.up.railway.app/boats/featured')
            .then((response) => response.json())
            .then((data) => {
                setGoods(data);
                // Randomly shuffle the data and slice the first 4 cards
                const shuffledData = shuffleArray(data);
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

    useEffect(() => {
        // Call the function to fetch and set the initial 4 cards
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
