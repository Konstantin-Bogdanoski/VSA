import React, {useState, useEffect} from 'react';
import axios from '../../../../axios/axios'
import {useParams} from "react-router";
import {withRouter} from 'react-router-dom';

const EditVideo = (props) => {
    const [video, setVideo] = useState({});
    const {id} = useParams();

    useEffect(() => {
        axios.get("/api/video/" + id).then((data) => {
            setVideo(data.data);
        });
    }, []);

    const onFormSubmit = (e) => {
        e.preventDefault();
        props.history.push('/ingredients');
        props.onSubmit(
            {
                "videoID": id,
                "name": e.target.name.value,
                "description" : e.target.description.value,
                "imdb_link" : e.target.imdbLink.value,
                "img_link" : e.target.imgLink.value,
            },
        );
    };

    const handleTermOnChange = (e) => {
        const paramName = e.target.name;
        const paramValue = e.target.value;
        setVideo({paramName: paramValue});
    };

    return (
        <div className="table-responsive">
            <form className="card" onSubmit={onFormSubmit}>
                <h4 className="text-upper text-left">Edit Video</h4>
                <div className="form-group row">
                    <label htmlFor="video" className="col-sm-4 offset-sm-1 text-left">Name</label>
                    <div className="col-sm-6">
                        <input type="text" onChange={handleTermOnChange} value={video.name}
                               className="form-control" id="video" name={"name"}
                               placeholder="Video name" required maxLength="50"/>
                    </div>
                </div>

                <div className="form-group row">
                    <label htmlFor="video" className="col-sm-4 offset-sm-1 text-left">Description</label>
                    <div className="col-sm-6">
                        <input type="text" onChange={handleTermOnChange} value={video.description}
                               className="form-control" id="video" name={"description"}
                               placeholder="Video description" required/>
                    </div>
                </div>

                <div className="form-group row">
                    <label htmlFor="video" className="col-sm-4 offset-sm-1 text-left">IMDB Link</label>
                    <div className="col-sm-6">
                        <input type="text" onChange={handleTermOnChange} value={video.imdbLink}
                               className="form-control" id="video" name={"imdbLink"}
                               placeholder="Video description" required/>
                    </div>
                </div>

                <div className="form-group row">
                    <label htmlFor="video" className="col-sm-4 offset-sm-1 text-left">Image Link</label>
                    <div className="col-sm-6">
                        <input type="text" onChange={handleTermOnChange} value={video.imgLink}
                               className="form-control" id="video" name={"imgLink"}
                               placeholder="Image link" required/>
                    </div>
                </div>

                <div className="form-group row">
                    <div
                        className="offset-sm-1 col-sm-3  text-center">
                        <button
                            type="submit"
                            className="btn btn-primary text-upper">
                            Save
                        </button>
                    </div>
                    <div
                        className="offset-sm-1 col-sm-3  text-center">
                        <button
                            className="btn btn-warning text-upper">
                            Reset
                        </button>
                    </div>
                    <div
                        className="offset-sm-1 col-sm-3  text-center">
                        <button
                            className="btn btn-danger text-upper">
                            Cancel
                        </button>
                    </div>
                </div>
            </form>
        </div>
    )
};

export default withRouter(EditVideo);