import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup} from "@angular/forms";
import {ChatbotService} from "../services/chatbot.service";

@Component({
  selector: 'app-chatbot-modal',
  templateUrl: './chatbot-modal.component.html',
  styleUrl: './chatbot-modal.component.css'
})
export class ChatbotModalComponent implements OnInit {

  botFormGroup!: FormGroup;
  chatHistory: { text: string, sender: string }[] = [];
  userInput: string = '';
  loading: boolean = false;
  showModal: boolean = false;

  constructor(private botService: ChatbotService, private fb: FormBuilder) {
    this.chatHistory.push({ text: 'Hello! How can I help you today?', sender: 'bot' }); 
    this.botFormGroup = this.fb.group({
      query: ['']
    });
  }

  ngOnInit(): void {
  }

  openModal() {
    this.showModal = true;
  }

  closeModal() {
    this.showModal = false;
  }

  onSubmit(): void {
    const query = this.userInput.trim();
    if (query) {
      // Add user query to chat history
      this.chatHistory.push({ text: query, sender: 'user' });
      // Clear user input
      this.userInput = '';
      // Set loading to true to show loading spinner
      this.loading = true;
      // Send query to bot service
      this.botService.sendPrompt(query).subscribe(response => {
        // Add bot response to chat history
        this.chatHistory.push({ text: response.result.result, sender: 'bot' });
        // Set loading to false to hide loading spinner
        this.loading = false;
      }, error => {
        console.error('Error getting bot response:', error);
        // Set loading to false to hide loading spinner
        this.loading = false;
      });
    }
  }

}
