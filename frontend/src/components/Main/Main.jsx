import './Main.css';
import {SectionCard} from "../SectionCard";


export const Main = () => {
  return (
    <main className='main'>
      <div className="main__img"></div>
      <button className='main__btn'>Follow your dream</button>
        <SectionCard/>
    </main>
  )
}