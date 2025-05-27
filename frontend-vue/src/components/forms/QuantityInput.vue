<template>
  <div class="flex items-center">
    <button
      type="button"
      class="flex items-center justify-center w-8 h-10 rounded-l-lg border border-gray-300 bg-transparent text-gray-600 shadow-theme-xs dark:border-gray-700 dark:bg-gray-900 dark:text-gray-200 focus:outline-hidden focus:ring-3 focus:ring-brand-500/10 dark:focus:border-brand-800 hover:bg-gray-200 hover:dark:bg-gray-600"
      @click="decrementValue"
    >
      <font-awesome-icon icon="minus"/>
    </button>
    <input
      type="text"
      :value="modelValue"
      @input="updateValue($event)"
      class="block w-14 text-center h-10 border-y border-gray-300 bg-transparent text-sm text-gray-800 shadow-theme-xs focus:border-brand-300 focus:outline-hidden focus:ring-3 focus:ring-brand-500/10 dark:border-gray-700 dark:bg-gray-900 dark:text-white/90"
    />
    <button
      type="button"
      class="flex items-center justify-center w-8 h-10 rounded-r-lg border border-gray-300 bg-transparent text-gray-600 shadow-theme-xs dark:border-gray-700 dark:bg-gray-900 dark:text-gray-200 focus:outline-hidden focus:ring-3 focus:ring-brand-500/10 dark:focus:border-brand-800 hover:bg-gray-200 hover:dark:bg-gray-600"
      @click="incrementValue"
    >
      <font-awesome-icon icon="plus"/>
    </button>
  </div>
</template>

<script setup>
import {defineEmits, defineProps} from 'vue';
import {FontAwesomeIcon} from '@fortawesome/vue-fontawesome';

const props = defineProps({
  modelValue: {
    type: [Number, String],
    required: true,
  },
  min: {
    type: Number,
    default: 1,
  },
  max: {
    type: Number,
    default: Infinity,
  },
  step: {
    type: Number,
    default: 1,
  }
});

const emit = defineEmits(['update:modelValue', 'change']);

function incrementValue() {
  let newValue = Number(props.modelValue) + props.step;
  if (newValue <= props.max) {
    emit('update:modelValue', newValue);
    emit('change');
  }
}

function decrementValue() {
  let newValue = Number(props.modelValue) - props.step;
  if (newValue >= props.min) {
    emit('update:modelValue', newValue);
    emit('change');
  }
}

function updateValue(event) {
  const value = event.target.value;
  const numberValue = value === '' ? props.min : Number(value);

  if (!isNaN(numberValue)) {
    emit('update:modelValue', numberValue);
    emit('change');
  }
}
</script>

<style scoped>

</style>
