import React from 'react';
import withRouter from "react-router/withRouter";
import {Link} from "react-router-dom";

/**
 * @author Natasha Stojanova (natashastojanova6@gmail.com)
 */
const Admin = (props) => {
    if (localStorage.getItem("auth_token") !== null && localStorage.getItem("auth_token") !== undefined) {
        let movies = props.videos.map(video => {
            return (
                <tr>
                    <td>
                        {video.id}
                    </td>
                    <td>
                        {video.name}
                    </td>
                    <td>
                        {video.upvotes}
                    </td>
                    <td>
                        {video.downvotes}
                    </td>
                    <td>
                        {video.dateCreated}
                    </td>
                    <td>
                        {video.dateUpdated}
                    </td>
                    <td>
                        <div className="col-md-6 text-right">
                            <Link to={"/admin/media/" + video.id + "/edit"}
                                  className="btn btn-sm btn-secondary">
                                <span className="fa fa-edit"/>
                                <span><strong>Edit</strong></span>
                            </Link>
                        </div>
                    </td>
                    <td>
                        <div className="col-md-6 text-right">
                            <Link to={"/admin"} onClick={() => {
                                props.onDelete(video.id)
                            }} className="btn btn-sm btn-outline-secondary ">
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
                    <Link to={"/admin/add"}>+ New Video</Link>
                    <h1>Videos</h1>
                </div>
                <table className="table table-bordered table-hover table-striped">
                    <thead>
                    <tr>
                        <th>
                            ID
                        </th>
                        <th>
                            Name
                        </th>
                        <th>
                            Upvotes
                        </th>
                        <th>
                            Downvotes
                        </th>
                        <th>
                            Created At
                        </th>
                        <th>
                            Updated At
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
        )
    } else
        return (
            <div className="container text-black-50">
                You are not permitted to view this page!
            </div>
        )
};

export default withRouter(Admin);