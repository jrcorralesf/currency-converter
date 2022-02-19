import Vue from 'vue'
import { required, email, numeric, double, between, min, max } from 'vee-validate/dist/rules'
import { extend, ValidationObserver, ValidationProvider, setInteractionMode } from 'vee-validate'

setInteractionMode('eager')
extend('required', {
  ...required,
  message: '{_field_} no puede estar vacío'
})
extend('between', {
  ...between,
  message: 'El campo {_field_} debe estar entre {min} y {max}'
})
extend('min', {
  ...min,
  message: '{_field_} may not be greater less {length} characters'
})
extend('max', {
  ...max,
  message: '{_field_} may not be greater than {length} characters'
})
extend('email', {
  ...email,
  message: '{_field_} debe ser un email valido'
})
extend('numeric', {
  ...numeric,
  message: 'Debe escribir números'
})

extend('double', {
  ...double,
  message: 'El campo {_field_} debe ser un decimal valido'
})

extend('secret', {
  validate: value => value === 'example',
  message: 'This is not the magic word'
})
// Register it globally
Vue.component('ValidationProvider', ValidationProvider)
Vue.component('ValidationObserver', ValidationObserver)
