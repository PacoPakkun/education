import React from 'react';
import './SwimApp.css'

class SwimRow extends React.Component {

    handleClicker = (event) => {
        console.log('delete button pentru ' + this.props.proba.id);
        this.props.deleteFunc(this.props.proba.id);
    }

    render() {
        return (
            <tr>
                <td>{this.props.proba.id}</td>
                <td>{this.props.proba.distanta}</td>
                <td>{this.props.proba.stil}</td>
                <td>
                    <button onClick={this.handleClicker}>Delete</button>
                </td>
            </tr>
        );
    }
}

/*<form onSubmit={this.handleClicke}><input type="submit" value="Delete"/></form>*/

class SwimTable extends React.Component {
    render() {
        var rows = [];
        var functieStergere = this.props.deleteFunc;
        this.props.probe.forEach(function (proba) {
            rows.push(<SwimRow proba={proba} key={proba.id} deleteFunc={functieStergere}/>);
        });
        return (<div className="SwimTable">

                <table className="center">
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>Distanta</th>
                        <th>Stil</th>
                        <th></th>
                    </tr>
                    </thead>
                    <tbody>{rows}</tbody>
                </table>

            </div>
        );
    }
}

export default SwimTable;