import React from 'react';
import { useLocation } from 'react-router-dom';
import { VesselForm } from '../VesselForm';

export const UpdatePage = () => {
    const location = useLocation();
    const initialData = location.state || null;

    return (
        <div>
            <h2>{initialData ? 'Update Yacht' : 'Create Yacht'}</h2>
            <VesselForm initialData={initialData} />
        </div>
    );
};

