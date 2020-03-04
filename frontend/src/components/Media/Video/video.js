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
                      className="card-link text-decoration-none text-white">
                    <div className="card mb-3 col-sm-4 bg-dark"
                         style={{maxWidth: 20 + "em", margin: 1 + "em"}}>
                        <div className="card-header bg-transparent">{props.video.name}</div>
                        <div className="card-body">
                            <img src={props.video.imgLink}
                                 className="card-img" alt=""/>
                        </div>
                        {/*<div
                            className="card-footer bg-transparent">Upvotes {props.video.upvotes} : {props.video.downvotes} Downvotes
                        </div>*/}
                    </div>
                </Link>
            </div>
        </div>
    );
};

export default Video;