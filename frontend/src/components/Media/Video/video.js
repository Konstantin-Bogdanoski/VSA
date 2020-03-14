import React from 'react';
import {Link} from "react-router-dom";

/**
 * @author Natasha Stojanova (natashastojanova6@gmail.com)
 */
const Video = (props) => {
    return (
        <div className="col-md-3 col-6">
            <div>
                <Link to={{
                    pathname: "/media/" + props.video.id,
                    name: props.video.name
                }}
                      className="card-link text-decoration-none text-black-50">
                    <div className="card mb-3 col-sm-4 bg-light border-warning"
                         style={{maxWidth: "20em", margin: "0.5em"}}>
                        <div className="card-header bg-transparent text-dark">{props.video.name} {props.video.hq ?
                            <span className="text-info text-sm-center">
                                1080p
                            </span> : <span/>}</div>
                        <div className="card-body">
                            <img src={props.video.imgLink}
                                 className="card-img"
                                 style={{width: "auto", height: "auto", maxHeight: "200px", maxWidth: "250px"}}
                                 alt=""/>
                        </div>
                    </div>
                </Link>
            </div>
        </div>
    );
};

export default Video;