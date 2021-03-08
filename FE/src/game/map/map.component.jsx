import React, { Component } from 'react'
import BattleComponent from './mapobjects/battle.component';
import FleetComponent from './mapobjects/fleet.component';
import PlanetComponent from './mapobjects/planet.component';
import mapService from './services/map.service'

class MapComponent extends Component {


    constructor(props) {
        super(props)

        const {match} = this.props;

        this.state = {
            id: match.params.id,
            planets: [],
            fleets: [],
            battles: [],
            loadInterval: 1000,
        }

    }

    componentDidMount() {
        const {  loadInterval,id: gameId } = this.state;
        const loadTimer = setInterval(() => {
            this.loadData();
        }, loadInterval);

        mapService.getPlanetsMap(gameId).then((res)=>{
            this.setState({planets: res.data});
            console.log(res.data);
        },(error)=>{});
    }

    loadData() {
        const {id: gameId}= this.state;
//        const {history} = this.props;
        //console.log("blabla");

        mapService.getFleetsMap(gameId).then((res)=>{
            this.setState({fleets: res.data});
            console.log(res.data);
        },(error)=>{
        });
        mapService.getBattlesMap(gameId).then((res)=>{
            this.setState({battles: res.data});
            console.log(res.data);
        },(error)=>{});
    }



    renderPlanets(planets) {
        return (planets.map((planet) =>
            <PlanetComponent x={planet.x} y={planet.y} />
        ));
    }

    renderBattles(battles) {
        return (battles.map((battles) =>
            <BattleComponent x={battles.x} y={battles.y} />
        ));
    }

    renderFleets(fleets) {
        return (fleets.map((fleet) =>
            <FleetComponent x={fleet.x} y={fleet.y} rot={this.getAngle(fleet.vy,fleet.vx)} />
        ));
    }

    getAngle(vx,vy){
        var theta = Math.atan2(vy, vx); // range (-PI, PI]
     theta *= 180 / Math.PI; // rads to degs, range (-180, 180]
    //if (theta < 0) theta = 360 + theta; // range [0, 360)
   // console.log("vx ",vx," vy ",vy,theta);
    return theta;
    }


    render() {
        const{planets,fleets,battles} = this.state;
        const style = {
            width: "800px",
            height: "600px",
            backgroundColor: "Khaki"
        }
        return (
            <div className="mx-auto" style={style}>
                {this.renderPlanets(planets)}
                {this.renderFleets(fleets)}
                {this.renderBattles(battles)}
            </div>
        )
    }
}

export default MapComponent