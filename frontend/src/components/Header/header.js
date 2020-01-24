import React, {Component} from 'react';
import './header.css';

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
                <header id="header">
                    <a className="logo" href="/">VSA</a>
                    <nav>
                        <a href="/login">Login</a>
                    </nav>
                </header>
            </div>
        );
    }
}

export default Header;