<template>
  <v-container class="positionStyle my-10 position: absolute; ">
    <h2 class="text-center my-10 ">Aplicación de divisas</h2>
    <v-row class="globeBorder my-10">
      <v-col cols="25">
        <v-tabs>
          <v-tab>Conversión</v-tab>
          <v-tab>Historial</v-tab>
          <v-tab-item>
            <Divisas />
          </v-tab-item>
          <v-tab-item>
            <Historial />
          </v-tab-item>
        </v-tabs>
      </v-col>
    </v-row>
  </v-container>
</template>
<script>
import Divisas from "@/components/converter.vue";
import Historial from "@/components/history.vue";
export default {
  name: "AppDivisas",
  components: {
    Divisas,
    Historial,
  }
};
</script>
<style>
.positionStyle {
  display: flex ;
  flex-direction: column;
  justify-content: center;
}
.globeBorder{
  border: 2px solid #a39e9e;
  border-radius: 8px;
  border-style: dotted;
}
</style>

