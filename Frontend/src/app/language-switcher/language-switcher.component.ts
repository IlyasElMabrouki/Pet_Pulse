import { Component } from '@angular/core';
import {TranslateService} from "@ngx-translate/core";

@Component({
  selector: 'app-language-switcher',
  templateUrl: './language-switcher.component.html',
  styleUrl: './language-switcher.component.css'
})
export class LanguageSwitcherComponent {
  selectedLanguage: string;

  constructor(private translate: TranslateService) {
    this.translate.setDefaultLang('en');

    const browserLang = this.translate.getBrowserLang();
    this.selectedLanguage = browserLang?.match(/en|fr|es|ar/) ? browserLang : 'en';
    this.updateDirection(this.selectedLanguage);
  }

  switchLanguage(lang: string) {
    this.selectedLanguage = lang;
    this.translate.use(lang);
    this.updateDirection(lang);
  }

  updateDirection(lang: string) {
    const dir = (lang === 'ar') ? 'rtl' : 'ltr';
    document.documentElement.setAttribute('dir', dir);
  }
}
