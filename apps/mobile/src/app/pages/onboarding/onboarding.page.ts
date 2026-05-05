import { Component } from '@angular/core';
import { Router } from '@angular/router';

interface Slide {
  icon: string;
  title: string;
  description: string;
}

@Component({
  selector: 'app-onboarding',
  templateUrl: './onboarding.page.html',
  styleUrls: ['./onboarding.page.scss'],
  standalone: false,
})
export class OnboardingPage {
  slides: Slide[] = [
    {
      icon: 'people-outline',
      title: 'Tu informacion en un solo lugar',
      description: 'Accede a tus datos personales, documentos y recibos de nomina de manera sencilla.',
    },
    {
      icon: 'gift-outline',
      title: 'Beneficios exclusivos',
      description: 'Descubre descuentos, seguros y beneficios disponibles para ti y tu familia.',
    },
    {
      icon: 'notifications-outline',
      title: 'Mantente informado',
      description: 'Recibe notificaciones importantes de tu empresa en tiempo real.',
    },
  ];

  currentIndex = 0;

  constructor(private router: Router) {}

  onSlideChange(event: any): void {
    this.currentIndex = event.detail?.[0]?.activeIndex ?? 0;
  }

  skip(): void {
    this.markComplete();
    this.router.navigate(['/login'], { replaceUrl: true });
  }

  next(): void {
    if (this.currentIndex < this.slides.length - 1) {
      this.currentIndex++;
    } else {
      this.markComplete();
      this.router.navigate(['/login'], { replaceUrl: true });
    }
  }

  private markComplete(): void {
    localStorage.setItem('dch_onboarding_done', 'true');
  }
}
