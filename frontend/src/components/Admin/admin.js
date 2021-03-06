import React from 'react';
import {Link, withRouter} from "react-router-dom";
import moment from "moment";

/**
 * @author Natasha Stojanova (natashastojanova6@gmail.com)
 */
const Admin = (props) => {
    if (localStorage.getItem("auth_token") !== null && localStorage.getItem("auth_token") !== undefined && props.videos !== undefined) {
        let movies = props.videos.map(video => {
            return (
                <tr className="text-center" style={{verticalAlign: "middle"}}>
                    <td style={{verticalAlign: "middle", width: "30%"}}>
                        {video.name}
                    </td>
                    <td style={{verticalAlign: "middle", width: "20%"}}>
                        {video.hq ? <span>1080p<br/></span> : ""}
                        {video.mq ? <span>720p<br/></span> : ""}
                        {video.lq ? <span>480p<br/></span> : ""}
                    </td>
                    <td style={{verticalAlign: "middle", width: "10%"}}>
                        {video.requests}
                    </td>
                    <td style={{verticalAlign: "middle", width: "20%"}} className="text-sm-center">
                        {moment(video.dateCreated).format("DD.MM.YYYY HH:mm")}
                    </td>
                    <td style={{verticalAlign: "middle", width: "20%"}}>
                        {moment(video.dateUpdated).format("DD.MM.YYYY HH:mm")}
                    </td>
                    <td style={{verticalAlign: "middle", width: "20%"}}>
                        <div className="col-md-6 text-center">
                            <Link to={"/admin/media/" + video.id + "/edit"}
                                  className="btn btn-sm btn-outline-success">
                                <span className="fa fa-pencil"/>
                            </Link>
                        </div>
                    </td>
                    <td style={{verticalAlign: "middle"}}>
                        <div className="col-md-6">
                            <Link to={"/admin"} onClick={() => {
                                props.onDelete(video.id)
                            }} className="btn btn-sm btn-outline-danger">
                                <span className="fa fa-remove"/>
                            </Link>
                        </div>
                    </td>
                </tr>
            )
        });

        return (
            <div className="container text-black-50">
                <div>
                    <div className="navbar-header">
                        <div style={{width: "33%", display: "inline-block"}}
                             className="text-danger text-left align-content-center"><h3>Videos</h3></div>
                        <div style={{width: "33%", display: "inline-block"}}/>
                        <div style={{width: "33%", display: "inline-block"}}>
                            <Link to={"/admin/add"} className="btn btn-outline-danger text-right">+ New
                                Video</Link>
                        </div>
                    </div>
                    <hr/>
                    {(props.videos === null || props.videos === undefined || props.videos.length === 0 ?
                        <div className="container">
                            <h1 className="text-center text-info">No videos available</h1>
                        </div> :
                        <table className="table table-bordered table-hover table-striped table-responsive-lg">
                            <thead className="table-warning text-center">
                            <tr>
                                <th style={{verticalAlign: "middle"}}>
                                    Name
                                </th>
                                <th style={{verticalAlign: "middle"}}>
                                    Supported Qualities
                                </th>
                                <th style={{verticalAlign: "middle"}}>
                                    Views
                                </th>
                                <th style={{verticalAlign: "middle"}}>
                                    Created
                                </th>
                                <th style={{verticalAlign: "middle"}}>
                                    Updated
                                </th>
                                <th style={{verticalAlign: "middle"}}>
                                    Update
                                </th>
                                <th style={{verticalAlign: "middle"}}>
                                    Delete
                                </th>
                            </tr>
                            </thead>
                            <tbody>
                            {movies}
                            </tbody>
                        </table>)}
                </div>
            </div>
        )
    } else
        return (
            <div className="container page-header text-danger">
                You are not permitted to view this page!
            </div>
        )
};

export default withRouter(Admin);