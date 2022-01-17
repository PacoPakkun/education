import React from 'react';
import './SwimForm.css'

class SwimForm extends React.Component {

    constructor(props) {
        super(props);
        this.state = {id: '', distanta: '', stil: ''};

        //  this.handleChange = this.handleChange.bind(this);
        // this.handleSubmit = this.handleSubmit.bind(this);
    }

    handleIdChange = (event) => {
        this.setState({id: event.target.value});
    }

    handleDistantaChange = (event) => {
        this.setState({distanta: event.target.value});
    }

    handleStilChange = (event) => {
        this.setState({stil: event.target.value});
    }

    handleSubmit = event => {
        if (event.nativeEvent.submitter.name === 'add') {
            var proba = {
                id: this.state.id,
                distanta: this.state.distanta,
                stil: this.state.stil
            }
            console.log('A proba was added: ');
            console.log(proba);
            this.props.addFunc(proba);
        } else if (event.nativeEvent.submitter.name === 'upd') {
            var id = this.state.id;
            var proba = {
                id: this.state.id,
                distanta: this.state.distanta,
                stil: this.state.stil
            }
            console.log('A proba was updated: ');
            console.log(proba);
            this.props.updFunc(id, proba);
        } else if (event.nativeEvent.submitter.name === 'find') {
            var id = this.state.id;
            console.log('A proba was searched: ');
            console.log(id);
            var proba = this.props.findFunc(id);
        }
        event.preventDefault();
    }

    render() {
        return (
            <form onSubmit={this.handleSubmit}>
                <label>
                    ID:
                    <input type="number" value={this.state.id} onChange={this.handleIdChange}/>
                </label><br/><br/>
                <label>
                    Distanta:
                    <input type="number" value={this.state.distanta} onChange={this.handleDistantaChange}/>
                </label><br/><br/>
                <label>
                    Stil:
                    <input type="text" value={this.state.stil} onChange={this.handleStilChange}/>
                </label><br/><br/>

                <input type="submit" value="Add" name="add"/>
                <input type="submit" value="Update" name="upd"/>
                <input type="submit" value="Find" name="find"/>
            </form>
        );
    }
}

export default SwimForm;