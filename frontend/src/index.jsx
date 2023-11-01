import 'bootstrap/dist/css/bootstrap.css';
import {createRoot} from 'react-dom/client';
import {App} from './App';


const element = document.querySelector('#root');
const root = createRoot(element);

root.render(
    <>
        <App/>
    </>
);
