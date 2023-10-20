import React from 'react';
import { useLocation } from 'react-router-dom';
import { CreateForm } from '../CreateForm';

export const UpdatePage = () => {
    const location = useLocation();
    const initialData = location.state || null;
    const id = initialData ? initialData.id : ''; // Extract the id

    return (
        <div>
            <h2>{initialData ? 'Update Yacht' : 'Create Yacht'}</h2>
            <CreateForm initialData={initialData} id={id} /> {/* Pass id to UpdateForm */}
        </div>
    );
};

