/**
 * @author Konstantin Bogdanoski (konstantin.b@live.com)
 */
import React, {Component} from 'react'
import {Link, withRouter} from 'react-router-dom';
import MediaService from "../../../repository/MediaService/mediaService";

class AddVideo extends Component {
    constructor(props) {
        super(props);
        this.state = props.state;
        this.setState({
            "waitResponse": false,
        })
    }

    handleTermOnChange = (e) => {
        e.preventDefault();
        const paramName = e.target.name;
        const paramValue = e.target.value;
        this.setState(state => {
            return {
                ...state,
                [paramName]: paramValue,
            }
        });
    };

    onFileChangeHandler = (e) => {
        e.preventDefault();
        this.setState({
            selectedFile: e.target.files[0]
        });
    };

    onSubmit = () => {
        this.setState({
            "waitResponse": true
        });
        const formData = new FormData();
        formData.append("file", this.state.selectedFile);
        formData.append("name", this.state.name);
        formData.append("description", this.state.description);
        formData.append("imdbLink", this.state.imdbLink);
        formData.append("imgLink", this.state.imgLink);
        MediaService.saveMovie(formData)
            .then(res => {
                this.setState({
                    "waitResponse": false,
                });
                MediaService.loadMedia().then(resp => {
                    this.setState(state => {
                        let media = resp.data;
                        return {
                            "media": media,
                        }
                    })
                }).catch(error => {
                    alert(error);
                });
                this.props.history.push("/admin");
            })
            .catch(error => {
                alert(error);
            })
    };

    render() {
        return (
            <div className="container">
                <div className="form-group">
                    <div className="col-md-1"/>
                    {(!this.state.waitResponse ?
                            <form>
                                <h4 className="text-upper text-left text-dark">Add Video</h4>
                                <div className="form-group row">
                                    <label htmlFor="video"
                                           className="col-sm-4 offset-sm-1 text-right text-dark">Name</label>
                                    <div className="col-sm-6">
                                        <input type="text" onChange={this.handleTermOnChange}
                                               className="form-control" id="video" name={"name"}
                                               placeholder="Video name" required maxLength="50"/>
                                    </div>
                                </div>

                                <div className="form-group row">
                                    <label htmlFor="description"
                                           className="col-sm-4 offset-sm-1 text-right text-dark">Description</label>
                                    <div className="col-sm-6">
                                        <input type="text" onChange={this.handleTermOnChange}
                                               className="form-control" id="description" name={"description"}
                                               placeholder="Video description" required maxLength="2000"/>
                                    </div>
                                </div>

                                <div className="form-group row">
                                    <label htmlFor="imdbLink" className="col-sm-4 offset-sm-1 text-right text-dark">IMDB
                                        Link</label>
                                    <div className="col-sm-6">
                                        <input type="text" onChange={this.handleTermOnChange}
                                               className="form-control" id="imdbLink" name={"imdbLink"}
                                               placeholder="IMDB Link" required maxLength="1000"/>
                                    </div>
                                </div>

                                <div className="form-group row">
                                    <label htmlFor="imgLink" className="col-sm-4 offset-sm-1 text-right text-dark">Image
                                        Link</label>
                                    <div className="col-sm-6">
                                        <input type="text" onChange={this.handleTermOnChange}
                                               className="form-control" id="imgLink" name={"imgLink"}
                                               placeholder="Image link" required maxLength="1000"/>
                                    </div>
                                </div>

                                <div className="form-group row">
                                    <label htmlFor="file" className="col-sm-4 offset-sm-1 text-right text-dark">Video
                                        File</label>
                                    <div className="col-md-6">
                                        <input type="file" className="form-control" name="file"
                                               onChange={this.onFileChangeHandler} required/>
                                    </div>
                                </div>

                                <div className="form-group row">
                                    <div
                                        className="offset-sm-1 col-sm-1  text-center">
                                    </div>
                                    <div
                                        className="offset-sm-1 col-sm-1  text-center">
                                    </div>
                                    <div
                                        className="offset-sm-1 col-sm-1  text-center">
                                        <button onClick={this.onSubmit}
                                                type="button"
                                                className="btn btn-primary text-upper">
                                            Save
                                        </button>
                                    </div>
                                    <div
                                        className="offset-sm-1 col-sm-1  text-center">
                                        <button
                                            type="reset"
                                            className="btn btn-warning text-upper">
                                            Reset
                                        </button>
                                    </div>
                                    <div
                                        className="offset-sm-1 col-sm-1  text-center">
                                        <Link to={"/admin"}
                                              className="btn btn-danger text-upper">
                                            Cancel
                                        </Link>
                                    </div>
                                </div>
                            </form>
                            :
                            <div className="container text-center text-dark font-weight-bold">
                                Please wait while we upload the video
                            </div>
                    )}
                </div>
            </div>
        )
    }
}

export default withRouter(AddVideo);