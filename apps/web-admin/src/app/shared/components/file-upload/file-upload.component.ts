import { Component, input, output, signal } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';

@Component({
  selector: 'app-file-upload',
  standalone: true,
  imports: [MatIconModule, MatButtonModule],
  templateUrl: './file-upload.component.html',
  styleUrl: './file-upload.component.scss',
})
export class FileUploadComponent {
  accept = input<string>('*');
  maxSize = input<number>(5); // MB
  label = input<string>('Upload file');

  fileSelected = output<File>();

  readonly fileName = signal<string | null>(null);
  readonly error = signal<string | null>(null);
  readonly isDragOver = signal<boolean>(false);

  onDragOver(event: DragEvent): void {
    event.preventDefault();
    event.stopPropagation();
    this.isDragOver.set(true);
  }

  onDragLeave(event: DragEvent): void {
    event.preventDefault();
    event.stopPropagation();
    this.isDragOver.set(false);
  }

  onDrop(event: DragEvent): void {
    event.preventDefault();
    event.stopPropagation();
    this.isDragOver.set(false);

    const files = event.dataTransfer?.files;
    if (files && files.length > 0) {
      this.processFile(files[0]);
    }
  }

  onFileInputChange(event: Event): void {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files.length > 0) {
      this.processFile(input.files[0]);
      input.value = '';
    }
  }

  private processFile(file: File): void {
    this.error.set(null);

    const maxBytes = this.maxSize() * 1024 * 1024;
    if (file.size > maxBytes) {
      this.error.set(
        `File size exceeds ${this.maxSize()}MB limit. Selected file is ${(file.size / (1024 * 1024)).toFixed(2)}MB.`
      );
      this.fileName.set(null);
      return;
    }

    if (this.accept() !== '*') {
      const acceptedTypes = this.accept()
        .split(',')
        .map((t) => t.trim());
      const fileExtension = '.' + file.name.split('.').pop()?.toLowerCase();
      const isAccepted = acceptedTypes.some(
        (type) =>
          file.type === type ||
          fileExtension === type ||
          (type.endsWith('/*') && file.type.startsWith(type.replace('/*', '/')))
      );

      if (!isAccepted) {
        this.error.set(`File type not allowed. Accepted: ${this.accept()}`);
        this.fileName.set(null);
        return;
      }
    }

    this.fileName.set(file.name);
    this.fileSelected.emit(file);
  }
}
