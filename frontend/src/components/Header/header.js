import React, {Component} from 'react';
import {Link} from "react-router-dom";

/**
 * @author Natasha Stojanova (natashastojanova6@gmail.com)
 */
class Header extends Component {
    constructor(props) {
        super(props);
    }

    render() {
        return (
            <div>
                <nav className="navbar-expand-lg navbar navbar-light bg-warning">
                    <a className="navbar-brand text-danger" href="/">VSA</a>
                    <button className="navbar-toggler" type="button" data-toggle="collapse"
                            data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent"
                            aria-expanded="false" aria-label="Toggle navigation">
                        <span className="navbar-toggler-icon"/>
                    </button>

                    <div className="collapse navbar-collapse" id="navbarSupportedContent">
                        <ul className="navbar-nav mr-auto">
                            <li className="nav-item active">
                                <a className="nav-link" href="/">Home <span className="sr-only">(current)</span></a>
                            </li>
                            <li className="nav-item">
                                <Link className="nav-link" to="/login">Login</Link>
                            </li>
                        </ul>
                        <form className="form-inline my-2 my-lg-0">
                            <input className="form-control mr-sm-2" type="search" placeholder="Let's find something"
                                   aria-label="Search"/>
                            <button className="btn btn-outline-danger my-2 my-sm-0" type="submit"
                                    onClick={this.props.searchTerm}>Search
                            </button>
                        </form>
                    </div>
                </nav>
            </div>
        );
    }
}

export default Header;