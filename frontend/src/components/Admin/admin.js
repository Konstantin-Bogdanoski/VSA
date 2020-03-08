import React, {useEffect, useState} from 'react';
import {Link, withRouter} from "react-router-dom";
import MediaService from "../../repository/MediaService/mediaService";

/**
 * @author Natasha Stojanova (natashastojanova6@gmail.com)
 */
const Admin = (props) => {
    let [videos, setVideos] = useState();
    useEffect(() => {
        MediaService.loadMedia().then(resp => {
            videos = resp.data;
            setVideos(videos);
        });
    }, []);

    if (localStorage.getItem("auth_token") !== null && localStorage.getItem("auth_token") !== undefined && videos !== undefined) {
        let movies = videos.map(video => {
            return (
                <tr>
                    <td>
                        {video.id}
                    </td>
                    <td>
                        {video.name}
                    </td>
                    <td>
                        {new Date(video.dateCreated).toLocaleString()}
                    </td>
                    <td>
                        {new Date(video.dateUpdated).toLocaleString()}
                    </td>
                    <td>
                        <div className="col-md-6 text-center">
                            <Link to={"/admin/media/" + video.id + "/edit"}
                                  className="btn btn-sm btn-info">
                                <span className="fa fa-edit"/>
                                <span><strong>Edit</strong></span>
                            </Link>
                        </div>
                    </td>
                    <td>
                        <div className="col-md-6">
                            <Link to={"/admin"} onClick={() => {
                                props.onDelete(video.id)
                            }} className="btn btn-sm btn-secondary">
                                <span className="fa fa-remove"/>
                                <span><strong>Remove</strong></span>
                            </Link>
                        </div>
                    </td>
                </tr>
            )
        });

        return (
            <div className="container text-black-50">
                <div>
                    <table className="table table-bordered table-hover table-striped table-responsive-lg">
                        <thead className="table-warning">
                        <tr>
                            <th colSpan={6}>
                                <div style={{width: "33%", display: "inline-block"}}
                                     className="text-danger text-left align-content-center"><h3>Videos</h3></div>
                                <div style={{width: "33%", display: "inline-block"}}/>
                                <div style={{width: "33%", display: "inline-block"}}>
                                    <Link to={"/admin/add"} className="btn btn-outline-danger text-right">+ New
                                        Video</Link>
                                </div>
                            </th>
                        </tr>
                        <tr>
                            <th>
                                ID
                            </th>
                            <th>
                                Name
                            </th>
                            <th>
                                Created
                            </th>
                            <th>
                                Updated
                            </th>
                            <th>
                                Edit
                            </th>
                            <th>
                                Delete
                            </th>
                        </tr>
                        </thead>
                        <tbody>
                        {movies}
                        </tbody>
                    </table>
                </div>
            </div>
        )
    } else
        return (
            <div className="container text-black-50">
                You are not permitted to view this page!
            </div>
        )
};

export default withRouter(Admin);