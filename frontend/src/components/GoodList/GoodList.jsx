import React, { useState, useEffect } from 'react';
import { GoodCard } from '../GoodCard';
import './GoodList.css';

export const GoodList = () => {
  const [goods, setGoods] = useState([]);

  useEffect(() => {
    // Fetch data from the JSON endpoint
    fetch('https://nyb-project-production.up.railway.app/boats/featured')
        .then((response) => response.json())
        .then((data) => setGoods(data))
        .catch((error) => console.error('Error fetching data:', error));
  }, []);

  return (
      <div className="GoodList">
        {goods.map((good) => (
            <GoodCard key={good.id} good={good} />
        ))}
      </div>
  );
};
