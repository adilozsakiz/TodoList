import React from 'react';
import { Modal, ModalHeader, ModalBody, FormGroup, Label, Input, ModalFooter, Button } from 'reactstrap';

function CustomModal(props) {
    return (
        <Modal isOpen={props.modalGet} toggle={() => { props.modalSet(!props.modalGet) }}>
        <ModalHeader toggle={() => { props.modalSet(!props.modalGet) }}>
          {props.modalData.header}
        </ModalHeader>
        <ModalBody>
          <FormGroup>
            <Label for="description">Description</Label>
            <Input defaultValue={props.selectedData?.description} disabled={props.modalData.inputsDisabled} id="description_input" />
          </FormGroup>
          <FormGroup>
            <Label for="date">Date</Label>
            <Input defaultValue={props.selectedData?.date} disabled={props.modalData.inputsDisabled} id="date_input" type="date" />
          </FormGroup>
        </ModalBody>
        <ModalFooter>
          <Button color={props.modalData.buttonColor} onClick={() => { props.save(); }}>{props.modalData.buttonName}</Button>
          <Button color="danger" onClick={() => { props.setSelectedData(); props.modalSet(false) }}>Cancel</Button>
        </ModalFooter>
      </Modal>
    )
}

export default CustomModal;