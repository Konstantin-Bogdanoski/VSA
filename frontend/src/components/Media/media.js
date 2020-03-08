import React from 'react';
import Video from "./Video/video";

/**
 * @author Natasha Stojanova (natashastojanova6@gmail.com)
 */
const Media = (props) => {
    let videos = props.videos.map(video => {
        return <Video video={video}/>
    });
    return (
        <div className="container-fluid">
            <div className="row">
                {videos}
            </div>
        </div>
    );
};

export default Media;