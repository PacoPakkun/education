import React, {useEffect, useRef, useState} from "react";
import Slider from "react-slick";
import "./Slider.css";
import "./Slider-Carousel.css";
import "./Slider-Carousel-theme.css";
import ArrowBackIosNewRoundedIcon from "@mui/icons-material/ArrowBackIosNewRounded";
import ArrowForwardIosRoundedIcon from "@mui/icons-material/ArrowForwardIosRounded";
import FiberManualRecordTwoToneIcon from "@mui/icons-material/FiberManualRecordTwoTone";
import ArrowRightTwoToneIcon from "@mui/icons-material/ArrowRightTwoTone";
import {TaskCard} from "./TaskCard";

export const SimpleSlider = (props) => {
    let sliderRef = useRef(null);

    React.useEffect(() => {
        setTimeout(() => {
            return sliderRef.current?.slickGoTo(props.initialSlide);
        }, 150)
    }, [])

    const getSettings = () => {
        return {
            dots: true,
            infinite: false,
            speed: 1500,
            slidesToShow: 4.3,
            slidesToScroll: 2,
            nextArrow: <NextArrow/>,
            prevArrow: <PrevArrow/>
        }
    };

    const getCardList = () => {
        return props.tasks?.map((day) => {
            const taskCards = day.map((item) => (
                <TaskCard
                    item={item}
                />
            ));
            return (
                <div className="card-list-container">
                    <span className="date">
                        {new Date(day[0].task.deadline).toDateString()}
                    </span>
                    <span className="dot">
                        <FiberManualRecordTwoToneIcon fontSize="small"/>
                    </span>
                    {taskCards}
                </div>
            );
        });
    };

    return (
        <React.Fragment>
            {props.tasks.length > 0 ?
                <div className="slider-component">
                    <div className="long-arrow">
                        <div className="little-arrow">
                            <ArrowRightTwoToneIcon fontSize="large"/>
                        </div>
                    </div>
                    <Slider ref={sliderRef} {...getSettings()}>{getCardList()}</Slider>
                </div>
                :
                <div className="slider-no-tasks">
                    No tasks? Go get <span className="orange">drunk</span>!
                </div>
            }
        </React.Fragment>
    );
};
export default SimpleSlider;

const NextArrow = (props) => {
    const {className, style, onClick} = props;
    return (
        <ArrowForwardIosRoundedIcon
            className={className}
            style={{
                ...style,
                display: "block",
                width: "40px",
                height: "40px",
                position: "absolute",
                top: "134px",
                right: "-50px",
                color: "#ff9f60",
            }}
            onClick={onClick}
        />
    );
}

const PrevArrow = (props) => {
    const {className, style, onClick} = props;
    return (
        <ArrowBackIosNewRoundedIcon
            className={className}
            style={{
                ...style,
                display: "block",
                width: "40px",
                height: "40px",
                position: "absolute",
                top: "134px",
                left: "-50px",
                color: "#ff9f60",
            }}
            onClick={onClick}
        />
    );
}
