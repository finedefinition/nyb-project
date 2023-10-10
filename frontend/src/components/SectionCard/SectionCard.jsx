import React from 'react';
import { Link } from 'react-router-dom'; // Import Link
import './SectionCard.css'; // Make sure to import your CSS file
import { GoodList } from '../GoodList'; // Assuming this is where your yacht items are displayed

export const SectionCard = () => {
    return (
        <section className="section">
            <div className="section__up">
                <h2 className='section__title'>Featured yachts</h2>

                {/* Add a Link component to the "See all" button */}
                <Link to="/yachts" className='section__btn'>
                    See all
                </Link>
            </div>
            {/* Display your yacht items using GoodList */}
            <GoodList />
        </section>
    );
};
