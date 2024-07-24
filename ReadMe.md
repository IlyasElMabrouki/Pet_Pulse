# PetPulse

PetPulse is an innovative application designed to help pet owners find their lost pets and facilitate the adoption of found animals. This application meets the needs of pet owners and individuals who have found animals, offering an efficient and accessible platform for reporting, searching, and adopting pets.

## Features
- **Report Lost and Found Pets**: Users can report lost pets and found animals with details and photos.
- **Search Functionality**: Advanced search capabilities to help users find lost pets or adopt found animals.
- **Chatbot Assistance**: A chatbot powered by Llama 2 to assist users in navigating the app and answering queries.

## Technologies
- **Frontend**: Angular
- **Backend**: Spring
- **Chatbot**: Llama 2

## Installation
1. **Clone the Repository**:
   ```bash
   git clone https://github.com/IlyasElMabrouki/Pet_Pulse
   cd petpulse
   ```

2. **Frontend Setup**:
   ```bash
   cd frontend
   npm install
   ng serve
   ```

3. **Backend Setup**:
   ```bash
   cd backend
   ./mvnw spring-boot:run
   ```

## Usage
- Open your browser and navigate to `http://localhost:4200` to access the frontend.
- Use the provided features to report lost or found pets, search for pets, and interact with the chatbot for assistance.

## Chatbot

### Prerequisites

Before you can start, make sure you have the following prerequisites installed on your system:

- Python 3.6 or higher
- Required Python packages (you can install them using pip):
  - langchain
  - sentence-transformers
  - faiss
  - PyPDF2 (for PDF document loading)
- Llama model: `llama-2-7b-chat.ggmlv3.q8_0.bin`

### Getting Started

To get started, you need to:

1. **Set up your environment and install the required packages**:
   ```bash
   pip install langchain sentence-transformers faiss PyPDF2
   ```

2. **Prepare the language model**.

3. **Create a vector database from a collection of PDF documents** using FAISS for fast similarity search and the Sentence Transformers model for generating embeddings:
   ```bash
   python ingest.py
   ```

4. **Start the bot by running the provided Python script** (`model.py`):
   ```bash
   python model.py
   ```

### Chatbot Usage

To use the bot, you can follow these steps:

1. **Start the bot** by running the provided Python script:
   ```bash
   python model.py
   ```

2. **Send a query to the bot**.

3. **The bot will provide a response** based on the information available in its database.