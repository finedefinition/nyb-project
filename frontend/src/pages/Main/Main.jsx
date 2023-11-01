import './Main.css';
import {SectionCard} from "../../components/SectionCard";

export const Main = () => {
  return (
        <main className='main'>
          <div className="main_banner">
              <img src="/Images/Main/banner-yacht.jpg" alt=""/>
              <div className="main_banner_container text-center">
                  <h1 className="main_banner_h"><p>Good quality</p> <p>& Easy to buy!</p></h1>
                  <button className="btn btn_primary btn_banner btn_bg_blue">Follow your dream</button>
              </div>
          </div>
            <SectionCard/>
        </main>
  )
}
