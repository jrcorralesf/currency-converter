<template>
  <v-container>
    <v-card>
      <v-col cols="12">
        <v-data-table
          :headers="headers"
          :items="historyData"
          :loading="loading"
          :options.sync="options"
          :server-items-length="totalDesserts"
          :footer-props="{ 'items-per-page-options': [10, 50, 100] }"
        >
        <template #[`item.actions`]="{ item }">
          <v-btn color="#FFB74D" @click="() =>deleteHistory(item.id)">Eliminar</v-btn>
        </template>
        </v-data-table>
      </v-col>
    </v-card>
  </v-container>
</template>


<script>
export default {
  name: "History",
  created() {
    this.getHistoryPage();
  },
  data() {
    return {
      apiHistoryList: "http://localhost:8080/history",
      apiDeleteHistory: "http://localhost:8080/history/delete/",
      headers: [
        {text: "Mod. Origen", align: "start", sortable: true, value: "sourceCurrency"},
        {text: "Mod. Objetivo", align: "start", sortable: true, value: "targetCurrency"},
        {text: "Monto", align: "start", sortable: true, value: "amount"},
        {text: "Conversión", align: "start", sortable: true, value: "conversionValue"},
        {text: "Fecha", align: "start", sortable: true, value: "conversionDate"},
        {text: "Opciones", align: "start", sortable: true, value: "actions"},
      ],
      historyData: [],
      loading: false,
      totalDesserts: 0,
      options: {
        startPage: 1,
        groupBy: [],
        groupDesc: [],
        itemsPerPage: 10,
        multiSort: false,
        mustSort: false,
        page: 1,
        sortBy: [],
        sortDesc: [],
      },
    };
  },
  watch: {
    options: {
      handler() {
        this.historyData = []
        this.getHistoryPage()
      },
      deep: true,
    },
    },
  methods: {
    async getHistoryPage(){
      this.loading = true;
      const apiWithParams = this.makeDatatableParamsOnApi()
      await this.$axios.get(apiWithParams).then(resp => {
        this.totalDesserts = resp.data.totalElements;
        const dataFromDb = resp.data.content;
        this.formatConversionDateAndConversion(dataFromDb);
        this.historyData = dataFromDb;
        });
      this.loading = false;
      
    },
    makeDatatableParamsOnApi(){
      const { sortBy, sortDesc, page, itemsPerPage, startPage } = this.options
      let sort = "-conversionDate"
      if (sortDesc[0] !==undefined ){
        sort = sortDesc[0] === true ? "-"+sortBy[0] : sortBy[0]  
      }
      return this.apiHistoryList + "?page=" + page + "&start_page=" + startPage + "&page_size=" + itemsPerPage +"&ordering="+sort
    },
    formatConversionDateAndConversion(dataFromDb){
      dataFromDb.forEach(item => {  
          const unformatedDate = new Date(item.conversionDate)
          const dateObject = {
            "day" : String(unformatedDate.getDate()).padStart(2, '0'),
            "month" : String(unformatedDate.getMonth() + 1).padStart(2, '0'),
            "year" : unformatedDate.getFullYear()
          }
          const timeOptions = {hour: 'numeric', minute: 'numeric', hour12: true};
          const formatedTime = new Intl.DateTimeFormat('en-US', timeOptions).format(unformatedDate);
          const formatedDate =  dateObject.day + '-' + dateObject.month + '-'
            + dateObject.year + ' ' + formatedTime;
          item.conversionDate = formatedDate
          item.conversionValue = (Math.round(item.conversionValue * 100) / 100).toFixed(2);
        })
    },
    async deleteHistory(id){
      const historyToDelete = this.apiDeleteHistory + id
      await this.$axios.delete(historyToDelete)
      this.getHistoryPage()
    }
  }
};
</script>
