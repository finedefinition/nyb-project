import { Amplify, Storage } from 'aws-amplify'; // Import Amplify and Storage
import { createRoot } from 'react-dom/client';
import { App } from './App';
import awsconfig from './components/SectionCard/aws-exports';

// Initialize and configure Amplify
Amplify.configure({
    ...awsconfig,
});

const element = document.querySelector('#root');
const root = createRoot(element);

root.render(
    <>
        <App />
    </>
);