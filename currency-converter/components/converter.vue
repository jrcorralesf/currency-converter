<template>
  <v-container fluid>
    <v-row>
      <v-col cols="12" sm="12" md="12" lg="12">
        <v-card>
          <v-card-text>
            <validation-observer ref="conversionObserver">
              <form ref="form" @submit.prevent="submit">
                <validation-provider
                  v-slot="{ errors }"
                  name="Monto"
                  rules="required|numeric"
                >
                  <v-text-field
                    v-model="amount"
                    label="Monto"
                    placeholder="Monto a convertir"
                    type="text"
                    append-icon="mdi-cash-multiple"
                    :error-messages="errors"
                  />
                </validation-provider>
                <validation-provider
                  v-slot="{ errors }"
                  name="Moneda origen"
                  rules="required"
                >
                  <v-autocomplete
                    v-model="selectedOrigin"
                    label="Moneda origen"
                    :items="DivisasObjectList"
                    :error-messages="errors"
                    itemText="completeName"
                    returnObject
                  />
                </validation-provider>
                <validation-provider
                  v-slot="{ errors }"
                  name="Moneda Objetivo"
                  rules="required"
                >
                  <v-autocomplete
                    v-model="selectedObjetive"
                    label="Moneda Objetivo"
                    :items="DivisasObjectList"
                    :error-messages="errors"
                    itemText="completeName"
                    returnObject
                  />
                </validation-provider>
              </form>
              <div class="text-center mt-5">
                <v-btn color="#FFB74D" @click="convert"
                  >Convertir</v-btn
                >
              </div>
            </validation-observer>
          </v-card-text>
        </v-card>
      </v-col>
      <v-col cols="12" sm="12" md="12" lg="12">
        <div v-if="conversionText.length > 0" class="text-center conversionTextContainer">
          <span>
            {{ conversionText }}
          </span>
        </div>
      </v-col>
    </v-row>
  </v-container>
</template>


<script>
import swal from "sweetalert";
export default {
  name: "DivisasComponent",
  data() {
    return {
      urlDivisasName:"https://openexchangerates.org/api/currencies.json?app_id=30b04e6d83f04d9eadc831e26eca12ab",
      urlBaseValue: "https://openexchangerates.org/api/latest.json?app_id=30b04e6d83f04d9eadc831e26eca12ab",
      ownApiToSave: "http://localhost:8080/history/save",
      DivisasObjectList: [],
      selectedOrigin: "",
      selectedObjetive: "",
      amount: "",
      conversionText: "",
      clearForm: false,
      format: new Intl.NumberFormat()
    };
  },
  created() {
    this.getDivisasList();
  },
  watch: {
    clearForm(flag) {
      if (flag) {
        this.selectedOrigin = "";
        this.selectedObjetive = "";
        this.amount = "";
        this.clearForm = false;
        this.$refs.conversionObserver.reset();
        this.$refs.form.reset();
      }
    }
  },
  methods: {
    async getDivisasList() {
      let divisasNames;
      await this.$axios.get(this.urlDivisasName).then(resp => {divisasNames = resp.data});
      let disasBaseValue;
      await this.$axios.get(this.urlBaseValue).then(resp => { disasBaseValue = resp.data.rates;});

      const acronymKeys = Object.keys(divisasNames);
      acronymKeys.forEach(acronym => {
        const name = divisasNames[acronym]
        const completeName = name +" "+ `(${acronym})`;
        const BaseValue = disasBaseValue[acronym]
        this.DivisasObjectList.push({
          completeName: completeName,
          name: name,
          acronym: acronym,
          rate: BaseValue
        });
      });

      this.DivisasObjectList.sort((a, b) => {
          if (a.completeName > b.completeName)
              return 1;
          if (a.completeName < b.completeName)
              return -1;
          return 0;
      });
    },
    convert() {
      this.$refs.conversionObserver.validate().then(valid => {
        if (valid) {
          if (this.amount > 0) {
            const sourceAcronym = this.selectedOrigin.acronym
            const targetAcronym = this.selectedObjetive.acronym
            const sourceRate=this.selectedOrigin.rate
            const targetRate=this.selectedObjetive.rate
            const total = (this.amount / sourceRate) * targetRate;

            const totalFormat = new Intl.NumberFormat({ style: "currency",
              currency: `${targetRate}` }).format(total);
            const amountFormat = new Intl.NumberFormat({ style: "currency",
              currency: `${sourceRate}`}).format(this.amount);
            this.conversionText = amountFormat+" "+sourceAcronym
              + " = "+ totalFormat+" "+targetAcronym;

            let conversionTosave = {
              "sourceCurrency": sourceAcronym,
              "targetCurrency": targetAcronym, 
              "amount": this.amount,
              "conversionValue": total };
            this.$axios.post(this.ownApiToSave, conversionTosave);

            this.clearForm = true;

          } else {
            swal("Error", "El monto debe ser mayor a 0", "error");
          }
        } else {
          swal("Error", "Debe llenar los datos adecuadamente", "error");
        }
      });
    },
  }
};
</script>


<style>
.conversionTextContainer {
  border: 2px solid #a39e9e;
  border-radius: 8px;
  border-style: solid;
  background-color: rgb(201, 201, 201);
}
.conversionTextContainer > span {
  line-height: 40px;
}
</style>
