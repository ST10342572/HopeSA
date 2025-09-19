
const express = require('express');
const mongoose = require('mongoose');
const path = require('path');
const app = express();

const itemsRoute = require('./crud/routes/items');

mongoose.connect('mongodb://localhost:27017/cruddb', {
  useNewUrlParser: true,
  useUnifiedTopology: true
});

app.use(express.json());
app.use(express.static(path.join(__dirname, 'crud/public')));
app.use('/api/items', itemsRoute);

app.listen(3000, () => console.log('Server running at http://localhost:3000'));
