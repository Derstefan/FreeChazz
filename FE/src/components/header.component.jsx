import React, { Component } from 'react'
import servcerService from '../services/server.service';

class HeaderComponent extends Component {
    constructor(props) {
        super(props)

        this.state = {
            gameNumber: 0
        }
    }

    componentDidMount() {
        servcerService.getServerData().then((res) => {
            console.log(res.data);
            this.setState({ gameNumber: res.data.gameNumber });

        }).catch((err) => console.log(err));
    }

    render() {
        const { gameNumber } = this.state;
        return (
            <div>
                <header>
                    <nav className="navbar navbar-expand-md navbar-dark bg-dark">
                        <div ><a className="navbar-brand text-muted">FreeChazz App</a> <small>Number of Games:{gameNumber}</small></div>
                    </nav>
                </header>
            </div>
        )
    }
}

export default HeaderComponent