import React from 'react';
import SwimTable from './SwimTable';
import './SwimApp.css'
import SwimForm from "./SwimForm";
import {GetProbe, DeleteProbe, AddProba, FindProba, UpdateProba} from './utils/rest-calls'


class SwimApp extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            probe: [{"id": "0", "distanta": "666", "stil": "bravo"}],
            deleteFunc: this.deleteFunc.bind(this),
            addFunc: this.addFunc.bind(this),
            updateFunc: this.updateFunc.bind(this),
            findFunc: this.findFunc.bind(this)
        }
        console.log('SwimApp constructor')
    }

    findFunc(id) {
        console.log('inside find Func ' + id);
        FindProba(id)
            .then(p => alert(p))
            .catch(error => console.log('eroare find', error));
    }

    addFunc(proba) {
        console.log('inside add Func ' + proba);
        AddProba(proba)
            .then(res => GetProbe())
            .then(probe => this.setState({probe}))
            .catch(erorr => console.log('eroare add ', erorr));
    }

    updateFunc(id, proba) {
        console.log('inside update Func ' + proba);
        UpdateProba(id, proba)
            .then(res => GetProbe())
            .then(probe => this.setState({probe}))
            .catch(error => console.log('eroare update', error));
    }

    deleteFunc(proba) {
        console.log('inside delete Func ' + proba);
        DeleteProbe(proba)
            .then(res => GetProbe())
            .then(probe => this.setState({probe}))
            .catch(error => console.log('eroare delete', error));
    }


    componentDidMount() {
        console.log('inside componentDidMount')
        GetProbe().then(probe => this.setState({probe}));
    }

    render() {
        return (
            <div className="SwimApp">
                <h1>Swim Contest Management</h1>
                <SwimForm addFunc={this.state.addFunc} updFunc={this.state.updateFunc} findFunc={this.state.findFunc}/>
                <br/>
                <br/>
                <SwimTable probe={this.state.probe} deleteFunc={this.state.deleteFunc}/>
            </div>
        );
    }
}

export default SwimApp;