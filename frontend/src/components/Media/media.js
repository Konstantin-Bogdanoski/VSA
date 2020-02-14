import React, {Component} from 'react';
import Video from "./Video/video";
import MediaService from "../../repository/MediaService/mediaService";

/**
 * @author Natasha Stojanova (natashastojanova6@gmail.com)
 */
const Media = (props) => {
    return (
        <div className="container">
            <div className="row">
                {props.videos}
            </div>
        </div>
    );
};

export default Media;