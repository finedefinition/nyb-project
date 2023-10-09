import { GoodList } from '../GoodList';
import './SectionCard.css';


export const SectionCard = () => {
    return (
        <section className="section">
            <div className="section__up">
                <h2 className='section__title'>Featured yachts</h2>
                <button className='section__btn'>See all</button>
            </div>
            <GoodList/>
        </section>
    )
}