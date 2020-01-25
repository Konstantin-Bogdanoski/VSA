import React, {Component} from 'react';

/**
 * @author Natasha Stojanova (natashastojanova6@gmail.com)
 */
class Video extends Component {
    constructor(props) {
        super(props);
    }

    render() {
        // TODO: Link the <a> to the videos location -> /media/{id}
        return (
            <div className="card mb-3 col-sm-4 bg-dark"
                 style={{maxWidth: 20 + "em", maxHeight: 20 + "em", margin: 1 + "em"}}>
                <div className="card-header bg-transparent">Video Name</div>
                <div className="card-body">
                    <p className="card-img">Image</p>
                </div>
                <div className="card-footer bg-transparent">Upvotes & Downvotes</div>
            </div>
        );
    }
}

export default Video;